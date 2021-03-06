package com.onlineclothing.springboot.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineclothing.springboot.entities.Discount;
import com.onlineclothing.springboot.repositories.DiscountRepository;

@Service
public class DiscountService {

	@Autowired
	private DiscountRepository discountRepository;

	public void updateStatus(Discount discount) {
		LocalDate startDate = discount.getStartDate();
		LocalTime startTime = discount.getStartTime();

		LocalDate endDate = discount.getEndDate();
		LocalTime endTime = discount.getEndTime();

		/**
		 * 			     start ----------------------------------------- end 
		 * now(isBefore)				   now 			         		      (isAfter)now
		 * 	Condition-1				    Condition-2					      	   Condition-3
		 */
		LocalDateTime startTimeStamp = LocalDateTime.of(startDate, startTime);
		LocalDateTime endTimeStamp = LocalDateTime.of(endDate, endTime);
		LocalDateTime currentTimeStamp = LocalDateTime.now();

		if (currentTimeStamp.isBefore(startTimeStamp)) {
			discount.setStatus("upcoming");
		} else if (currentTimeStamp.isAfter(endTimeStamp)) {
			discount.setStatus("expired");
		} else {
			discount.setStatus("live");
		}
	}

	public List<Discount> updateDiscount(){
		List<Discount> discounts = discountRepository.findAll();
		for (Discount discount : discounts) {
			updateStatus(discount);
		}
		List<Discount> updatedDiscounts = discountRepository.saveAll(discounts);
		return updatedDiscounts;
	}

	public Discount newDiscount(Discount discount){
		Discount savedDiscount = null;
		if(discount != null) {
			updateStatus(discount);
			savedDiscount = discountRepository.save(discount);
		}
		return savedDiscount;
	}

	public List<Discount> getDiscountByCategory(Integer categoryid) {
		updateDiscount();
		return discountRepository.findByCategoryid(categoryid);
	}
	
	public Discount getLiveDiscountByCategory(Integer categoryid) {
		updateDiscount();
		return discountRepository.findTop1ByCategoryidAndStatusOrderByDiscountPercentDesc(categoryid,"live");
	}
	
	public long getDiscountCount() {
		return discountRepository.count();
	}
	
	public void deleteByDiscountid(int id) {
		discountRepository.deleteByDiscountid(id);
	}

}
