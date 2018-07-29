package cn.edu.cqu.wheelProject.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.edu.cqu.wheel.framework.util.MapTrunPojoUtil;
import cn.edu.cqu.wheelProject.domain.Customer;
import cn.edu.cqu.wheelProject.service.CustomerService;





/**
 * 创建客户
 */
@WebServlet("/customer_create")
public class CustomerAddController extends HttpServlet{
	
	private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }
	/**
     * 进入 创建客户 界面
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	Customer customer = new Customer();
    	customer.setName(req.getParameter("name"));
    	customer.setName(req.getParameter("contact"));
    	customerService.createCustomer(MapTrunPojoUtil.object2Map(customer));
    }

    /**
     * 处理 创建客户 请求
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO
    }
}
