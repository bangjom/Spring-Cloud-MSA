package com.example.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class ZuulLogginFilter extends ZuulFilter {
    @Override
    public String filterType() { // 전후 선택
        return "pre";
    }

    @Override
    public int filterOrder() { //순서
        return 1;
    }

    @Override
    public boolean shouldFilter() { //사용 여부
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("************** printing logs: ");
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        log.info("************** printing logs: "+ request.getRequestURI());
        return null;
    }
}
