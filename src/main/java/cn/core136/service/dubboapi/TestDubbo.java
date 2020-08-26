package cn.core136.service.dubboapi;

import com.alibaba.dubbo.config.annotation.Service;

@Service(version = "${dubbo.service.version}")
public class TestDubbo {
	public String  dubboTest() {
		return "hello dubbo";
	}
}
