package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.domain.OrderDetailVOList;
import com.demo.domain.OrderListVO;
import com.demo.domain.OrderVO;
import com.demo.domain.ProductVO;
import com.demo.dto.MemberDTO;
import com.demo.dto.SearchDateDTO;
import com.demo.service.MemberService;
import com.demo.service.OrderService;
import com.demo.service.ProductService;
import com.demo.util.Criteria;
import com.demo.util.PageMaker;

@Controller
@RequestMapping(value="/order/*")
public class OrderController {
	
	@Inject
	private OrderService service;
	
	@Inject
	private ProductService productService;
	
	@Inject
	private MemberService memberService;
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	/* 
	 * 상품 상세 -> 구매 또는 바로 구매 
	 * 
	 * model로 상품리스트, 수량리스트, 구매자 정보 전달
	 */
	@RequestMapping(value="buy", method=RequestMethod.GET)
	public void buyGET(@RequestParam int ord_amount,
					   @RequestParam int pdt_num, Model model, HttpSession session) throws Exception {
		
		logger.info("=====buyGET() execute...");
		
		List<ProductVO> productList = new ArrayList<ProductVO>(); 
		List<Integer> amountList = new ArrayList<Integer>();
		
		productList.add(productService.readProduct(pdt_num)); 
		amountList.add(ord_amount); 
		
		model.addAttribute("productList", productList);
		model.addAttribute("amountList", amountList);
		
		MemberDTO dto = (MemberDTO)session.getAttribute("user");
		model.addAttribute("user", memberService.readUserInfo(dto.getMem_id()));
	}
	
	
	/*
	 * 장바구니 -> 구매(단일 상품)
	 * 
	 * model로 상품리스트, 수량리스트, 구매자 정보 전달
	 */
	@RequestMapping(value="buyFromCart", method=RequestMethod.GET)
	public String buyFromCartGET(@RequestParam int ord_amount,
					   @RequestParam int pdt_num, Model model, HttpSession session) throws Exception {
		
		logger.info("=====buyFromCartGET() execute...");
		
		List<ProductVO> productList = new ArrayList<ProductVO>(); 
		List<Integer> amountList = new ArrayList<Integer>();
		
		productList.add(productService.readProduct(pdt_num)); 
		amountList.add(ord_amount); 
		
		model.addAttribute("productList", productList);
		model.addAttribute("amountList", amountList);
		
		MemberDTO dto = (MemberDTO)session.getAttribute("user");
		model.addAttribute("user", memberService.readUserInfo(dto.getMem_id()));
		
		return "/order/buyFromCart";
	}
	
	/*
	 * 장바구니 -> 체크상품 구매(다수 상품)
	 * 
	 * model로 상품리스트, 수량리스트, 구매자 정보 전달
	 */
	@RequestMapping(value="buyFromCart", method=RequestMethod.POST)
	public void buyFromCartPOST(@RequestParam("check") List<Integer> checkList,
						@RequestParam("pdt_num") List<Integer> pdt_numList,
						@RequestParam("cart_amount") List<Integer> cart_amountList,
						@RequestParam("cart_code") List<Integer> cart_codeList,
						Model model, HttpSession session) throws Exception {
		
		logger.info("=====buyFromCartPOST() execute...");
		logger.info("check :" + checkList.toString());
		logger.info("pdt_num :" + pdt_numList.toString());
		logger.info("cart_amount :" + cart_amountList.toString());
		logger.info("cart_code :" + cart_codeList.toString());
		
		
		List<ProductVO> productList = new ArrayList<ProductVO>();
		List<Integer> amountList = new ArrayList<Integer>();
		
		// 장바구니 목록에서 체크된 값만을 선택하여 List에 추가
		for(int i=0; i<checkList.size(); i++) {
			for(int j=0; j<cart_codeList.size(); j++) {
				if(checkList.get(i).equals(cart_codeList.get(j))) {
					productList.add(productService.readProduct((int)pdt_numList.get(i)));
					amountList.add(cart_amountList.get(i));
					continue;
					
				} else {
					continue;
				}
				
			}
		}
		model.addAttribute("productList", productList);
		model.addAttribute("amountList", amountList);
		
		MemberDTO dto = (MemberDTO)session.getAttribute("user");
		model.addAttribute("user", memberService.readUserInfo(dto.getMem_id()));
		
	}
	
	
	
	/* 
	 * 상품 상세 -> 구매 또는 바로구매
	 * 실제 구매 DB작업
	 */
	@RequestMapping(value="order", method=RequestMethod.POST)
	public String orderPOST(OrderVO order, 
						  OrderDetailVOList orderDetailList,
						  Model model, HttpSession session) throws Exception {
		
		logger.info("=====orderPOST() execute...");
		
		logger.info("=====OrderVO(주문자 정보): " + order.toString());
		logger.info("=====OrderDetail(주문 내역): " + orderDetailList.toString());
		
		service.addOrder(order, orderDetailList);
		
		return "/order/orderComplete";
	}
	
	/* 
	 * 장바구니 -> 구매
	 * 실제 구매 DB작업
	 */
	@RequestMapping(value="orderFromCart", method=RequestMethod.POST)
	public String orderFromCartPOST(OrderVO order, 
						  OrderDetailVOList orderDetailList,
						  Model model, HttpSession session) throws Exception {
		
		logger.info("=====orderFromCartPOST() execute...");
		
		logger.info("=====OrderVO(주문자 정보): " + order.toString());
		logger.info("=====OrderDetail(주문 내역): " + orderDetailList.toString());
		
		MemberDTO dto = (MemberDTO)session.getAttribute("user");
		service.addOrderCart(order, orderDetailList, dto.getMem_id());
		
		return "/order/orderComplete";
	}

	/* 
	 * 주문 조회 작업(GET)
	 */
	@RequestMapping(value="list")
	public void listGET(@ModelAttribute("search") SearchDateDTO search,
					Criteria cri, Model model, HttpSession session) throws Exception {
		
		logger.info("=====listGET() execute...");
		
		MemberDTO dto = (MemberDTO) session.getAttribute("user");
		List<OrderListVO> list = service.orderList(dto.getMem_id(), cri, search);
		
		model.addAttribute("orderList", list);
		
		// PageMaker 생성
		PageMaker pm = new PageMaker();
		pm.setCri(cri);
		int count = service.countList(dto.getMem_id(), search);
		pm.setTotalCount(count);
		
		model.addAttribute("pm", pm);
		
	}
	
	/*
	 * 주문 상세 조회(GET)
	 */
	@RequestMapping(value="read", method=RequestMethod.GET)
	public void readGET(int odr_code, Model model, HttpSession session) throws Exception {
		
		logger.info("=====readGET() execute...");
		
		model.addAttribute("orderList", service.readOrder(odr_code));
		model.addAttribute("order", service.getOrder(odr_code));
		
	}
}
