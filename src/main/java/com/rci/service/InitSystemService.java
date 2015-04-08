package com.rci.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rci.bean.DishDTO;
import com.rci.bean.DishTypeDTO;
import com.rci.bean.PaymodeDTO;
import com.rci.bean.entity.Dish;
import com.rci.bean.entity.DishType;
import com.rci.bean.entity.Paymode;
import com.rci.bean.entity.Scheme;
import com.rci.bean.entity.account.Account;
import com.rci.constants.BusinessConstant;
import com.rci.constants.enums.SchemeType;
import com.rci.datafetch.IDataFetchService;

@Service("InitSystemService")
public class InitSystemService {
	@Resource(name="DataFetchService")
	private IDataFetchService dataFetch;
	@Autowired
	private Mapper beanMapper;
	@Resource(name="DishService")
	private IDishService dishService;
	@Resource(name="DishTypeService")
	private IDishTypeService dishTypeService;
	@Resource(name="FetchMarkService")
	private IFetchMarkService markService;
	@Resource(name="PayModeService")
	private IPayModeService paymodeService;
	@Resource(name="AccountService")
	private IAccountService accService;
	public void init(){
		if(markService.isSystemInit()){
			return;
		}
		//初始化菜品
		List<DishTypeDTO> typeDTOs = dataFetch.fetchDishType();
//		DishType[] types = new DishType[typeDTOs.size()];
		for(int i=0;i<typeDTOs.size();i++){
			DishTypeDTO typeDTO = typeDTOs.get(i);
			DishType type = beanMapper.map(typeDTO, DishType.class);
			List<DishDTO> dishDTOs = dataFetch.fetchDishByTypeNo(type.getDtNo());
			List<Dish> dishes = new LinkedList<Dish>();
			for(DishDTO dishDTO:dishDTOs){
				Dish dish = beanMapper.map(dishDTO, Dish.class);
				dish.setDishType(type);
				dishes.add(dish);
				dishService.rwSaveDish(dish);
			}
			type.setDishes(dishes);
//			types[i] = type;
		}
		
		//初始化支付方式
		List<PaymodeDTO> paymodeDTOs = dataFetch.fetchPaymodes();
		List<Paymode> paymodes = new ArrayList<Paymode>();
		List<Account> accounts = new ArrayList<Account>();
		for(PaymodeDTO modeDTO:paymodeDTOs){
			Paymode mode = beanMapper.map(modeDTO, Paymode.class);
			if(BusinessConstant.CASH_NO.equals(mode.getModeNo())){
				Scheme s1 = new Scheme(SchemeType.NODISCOUNT,"无折扣");
				s1.setPaymode(mode);
				Scheme s2 = new Scheme(SchemeType.EIGHTDISCOUNT,"8折优惠活动");
				s2.setPaymode(mode);
				List<Scheme> schemes = new ArrayList<Scheme>();
				schemes.add(s1);
				schemes.add(s2);
				mode.setSchemes(schemes);
			}
			if(BusinessConstant.DPTG_NO.equals(mode.getModeNo())){
				Scheme s1 = new Scheme(SchemeType.FIFITY,"50元代金券");
				s1.setPrice(new BigDecimal(50));
				s1.setPostPrice(new BigDecimal(44));
				s1.setCommission(new BigDecimal(1));
				s1.setPaymode(mode);
				Scheme s2 = new Scheme(SchemeType.HUNDRED,"100元代金券");
				s2.setPrice(new BigDecimal(100));
				s2.setPostPrice(new BigDecimal(88));
				s2.setCommission(new BigDecimal(1));
				s2.setPaymode(mode);
				Scheme s3 = new Scheme(SchemeType.BIG_SUIT_A,"大份套餐(香辣)");
				s3.setPrice(new BigDecimal(98));
				s3.setPostPrice(new BigDecimal(98));
				s3.setCommission(new BigDecimal(1));
				s3.setSpread(new BigDecimal(0.1));
				s3.setPaymode(mode);
				Scheme s4 = new Scheme(SchemeType.BIG_SUIT_B,"大份套餐(甜辣)");
				s4.setPrice(new BigDecimal(98));
				s4.setPostPrice(new BigDecimal(98));
				s4.setCommission(new BigDecimal(1));
				s4.setSpread(new BigDecimal(0.1));
				s4.setPaymode(mode);
				Scheme s5 = new Scheme(SchemeType.SMALL_SUIT,"小份套餐");
				s5.setPrice(new BigDecimal(50));
				s5.setPostPrice(new BigDecimal(50));
				s5.setCommission(new BigDecimal(1));
				s5.setSpread(new BigDecimal(0.1));
				s5.setPaymode(mode);
				Scheme s6 = new Scheme(SchemeType.SUIT_A,"套餐A");
				s6.setPrice(new BigDecimal(32));
				s6.setPostPrice(new BigDecimal(32));
				s6.setCommission(new BigDecimal(1));
				s6.setPaymode(mode);
				Scheme s7 = new Scheme(SchemeType.SUIT_B,"套餐B");
				s7.setPrice(new BigDecimal(68));
				s7.setPostPrice(new BigDecimal(68));
				s7.setCommission(new BigDecimal(1));
				s7.setPaymode(mode);
				Scheme s8 = new Scheme(SchemeType.SUIT_C,"套餐C（原味）");
				s8.setPrice(new BigDecimal(98));
				s8.setPostPrice(new BigDecimal(98));
				s8.setCommission(new BigDecimal(1));
				s8.setPaymode(mode);
				Scheme s9 = new Scheme(SchemeType.SUIT_C2,"套餐C（香辣）");
				s9.setPrice(new BigDecimal(98));
				s9.setPostPrice(new BigDecimal(98));
				s9.setCommission(new BigDecimal(1));
				s9.setPaymode(mode);
				List<Scheme> schemes = new ArrayList<Scheme>();
				schemes.add(s1);
				schemes.add(s2);
				schemes.add(s3);
				schemes.add(s4);
				schemes.add(s5);
				schemes.add(s6);
				schemes.add(s7);
				schemes.add(s8);
				schemes.add(s9);
				mode.setSchemes(schemes);
			}
			if( BusinessConstant.MT_NO.equals(mode.getModeNo())){
				Scheme s1 = new Scheme(SchemeType.FIFITY,"50元代金券");
				s1.setPrice(new BigDecimal(50));
				s1.setPostPrice(new BigDecimal(44));
				s1.setCommission(new BigDecimal(1));
				s1.setPaymode(mode);
				Scheme s2 = new Scheme(SchemeType.HUNDRED,"100元代金券");
				s2.setPrice(new BigDecimal(100));
				s2.setPostPrice(new BigDecimal(88));
				s2.setCommission(new BigDecimal(1));
				s2.setPaymode(mode);
				Scheme s3 = new Scheme(SchemeType.BIG_SUIT_A,"大份套餐(香辣)");
				s3.setPrice(new BigDecimal(98));
				s3.setPostPrice(new BigDecimal(98));
				s3.setCommission(new BigDecimal(1));
				s3.setPaymode(mode);
				Scheme s4 = new Scheme(SchemeType.BIG_SUIT_B,"大份套餐(甜辣)");
				s4.setPrice(new BigDecimal(98));
				s4.setPostPrice(new BigDecimal(98));
				s4.setCommission(new BigDecimal(1));
				s4.setPaymode(mode);
				Scheme s5 = new Scheme(SchemeType.SMALL_SUIT,"小份套餐");
				s5.setPrice(new BigDecimal(50));
				s5.setPostPrice(new BigDecimal(50));
				s5.setCommission(new BigDecimal(1));
				s5.setPaymode(mode);
				Scheme s6 = new Scheme(SchemeType.SUIT_A,"套餐A");
				s6.setPrice(new BigDecimal(32));
				s6.setPostPrice(new BigDecimal(32));
				s6.setCommission(new BigDecimal(1));
				s6.setPaymode(mode);
				Scheme s7 = new Scheme(SchemeType.SUIT_B,"套餐B");
				s7.setPrice(new BigDecimal(68));
				s7.setPostPrice(new BigDecimal(68));
				s7.setCommission(new BigDecimal(1));
				s7.setPaymode(mode);
				Scheme s8 = new Scheme(SchemeType.SUIT_C,"套餐C(原味)");
				s8.setPrice(new BigDecimal(98));
				s8.setPostPrice(new BigDecimal(98));
				s8.setCommission(new BigDecimal(1));
				s8.setPaymode(mode);
				Scheme s9 = new Scheme(SchemeType.SUIT_C2,"套餐C（香辣）");
				s9.setPrice(new BigDecimal(98));
				s9.setPostPrice(new BigDecimal(98));
				s9.setCommission(new BigDecimal(1));
				s9.setPaymode(mode);
				List<Scheme> schemes = new ArrayList<Scheme>();
				schemes.add(s1);
				schemes.add(s2);
				schemes.add(s3);
				schemes.add(s4);
				schemes.add(s5);
				schemes.add(s6);
				schemes.add(s7);
				schemes.add(s8);
				schemes.add(s9);
				mode.setSchemes(schemes);
			}
			if(BusinessConstant.LS_NO.equals(mode.getModeNo())){
				Scheme s1 = new Scheme(SchemeType.FIFITY,"50元代金券");
				s1.setPrice(new BigDecimal(50));
				s1.setPostPrice(new BigDecimal(44));
				s1.setCommission(new BigDecimal(1));
				s1.setSpread(new BigDecimal(-0.06));
				s1.setPaymode(mode);
				Scheme s2 = new Scheme(SchemeType.HUNDRED,"100元代金券");
				s2.setPrice(new BigDecimal(100));
				s2.setPostPrice(new BigDecimal(88));
				s2.setCommission(new BigDecimal(1));
				s2.setSpread(new BigDecimal(-0.02));
				s2.setPaymode(mode);
				Scheme s6 = new Scheme(SchemeType.SUIT_A,"套餐A");
				s6.setPrice(new BigDecimal(32));
				s6.setPostPrice(new BigDecimal(32));
				s6.setCommission(new BigDecimal(1));
				s6.setSpread(new BigDecimal(-0.08));
				s6.setPaymode(mode);
				Scheme s7 = new Scheme(SchemeType.SUIT_B,"套餐B");
				s7.setPrice(new BigDecimal(68));
				s7.setPostPrice(new BigDecimal(68));
				s7.setCommission(new BigDecimal(1));
				s7.setSpread(new BigDecimal(-0.02));
				s7.setPaymode(mode);
				Scheme s8 = new Scheme(SchemeType.SUIT_C,"套餐C（原味）");
				s8.setPrice(new BigDecimal(98));
				s8.setPostPrice(new BigDecimal(98));
				s8.setCommission(new BigDecimal(1));
				s8.setSpread(new BigDecimal(-0.02));
				s8.setPaymode(mode);
				Scheme s9 = new Scheme(SchemeType.SUIT_C2,"套餐C（香辣）");
				s9.setPrice(new BigDecimal(98));
				s9.setPostPrice(new BigDecimal(98));
				s9.setCommission(new BigDecimal(1));
				s9.setPaymode(mode);
				List<Scheme> schemes = new ArrayList<Scheme>();
				schemes.add(s1);
				schemes.add(s2);
				schemes.add(s6);
				schemes.add(s7);
				schemes.add(s8);
				schemes.add(s9);
				mode.setSchemes(schemes);
			}
			if(BusinessConstant.DPSH_NO.equals(mode.getModeNo())){
				Scheme s = new Scheme(SchemeType.CASHBACK,"满100减30");
				s.setCommission(new BigDecimal(1));
				s.setPrice(new BigDecimal(30));
				s.setPaymode(mode);
				List<Scheme> schemes = new ArrayList<Scheme>();
				schemes.add(s);
				mode.setSchemes(schemes);
			}
			paymodes.add(mode);
			Account account = new Account(mode.getModeNo());
			account.setAccountName(mode.getModeName()+"账户");
			accounts.add(account);
		}
		paymodeService.rwCreatePayMode(paymodes.toArray(new Paymode[0]));
		//初始化账户信息
		accService.rwCreateAccout(accounts.toArray(new Account[0]));
		
		markService.rwSystemInit();//系统初始化完成标记
	}
	
}
