package cn.edu.cqu.wheelProject.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.edu.cqu.wheel.framework.anotation.Action;
import cn.edu.cqu.wheel.framework.anotation.Controller;
import cn.edu.cqu.wheel.framework.anotation.Inject;
import cn.edu.cqu.wheel.framework.bean.Data;
import cn.edu.cqu.wheel.framework.bean.View;
import cn.edu.cqu.wheelProject.domain.Customer;
import cn.edu.cqu.wheelProject.service.CustomerService;


/**
 * 处理客户管理相关请求
 */
@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;
    
    /**
     * 进入 客户列表 界面
     */
    @Action("get:/customer")
    public View index(HttpServletRequest request) {
        List<Customer> customerList = customerService.getCustomerList();
        return new View("customer.jsp").addModel("customerList", customerList);
    }
    /**
     * 进入 客户列表 界面
     */
    @Action("get:/customerJson")
    public Data getCustomerJson(HttpServletRequest request) {
        List<Customer> customerList = customerService.getCustomerList();
        return new Data(customerList);
    }
    
}