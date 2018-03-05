package com.taotao.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 处理后台管理系统中的页面转发
 *
 */
@RequestMapping("/page")
@Controller
public class PageController {

	@RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
	public String toPage(@PathVariable String pageName) {
		return pageName;
	}
}
