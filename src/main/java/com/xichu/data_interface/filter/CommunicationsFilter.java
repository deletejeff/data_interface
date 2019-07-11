package com.xichu.data_interface.filter;

import com.alibaba.fastjson.JSON;
import com.xichu.data_interface.bean.ResultMap;
import com.xichu.data_interface.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@WebFilter(urlPatterns = "/data_receive/*")
public class CommunicationsFilter implements Filter {
    
    public void destroy() {
        log.info("++++++++++++++++++++++++destroy++++++++++++++++++++++++");
    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        MDC.put("sessionId",request.getSession().getId());
        log.info("accessURI=" + request.getRequestURI());
        StringBuilder strBuff = new StringBuilder();
        InputStreamReader bin;
        BufferedReader reader = null;
        try {
            bin = new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8);
            reader = new BufferedReader(bin);
            String line;
            while ((line = reader.readLine()) != null) {
                strBuff.append(line);
            }
            String receiveData = strBuff.toString();
            log.info("receiveData:" + receiveData);
            request.setAttribute("receiveData", receiveData);
            arg2.doFilter(arg0, arg1);
        } catch (Exception e) {
            log.error("Exception", e);
            ResultMap resultMap = new ResultMap(ResultEnum.ERROR);
            arg1.setContentType("application/json;charset=UTF-8");
            arg1.getWriter().write(JSON.toJSONString(resultMap));
            arg1.getWriter().close();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("IOException", e);
                }
            }
            if (request.getInputStream() != null) {
                try {
                    request.getInputStream().close();
                } catch (IOException e) {
                    log.error("IOException", e);
                }
            }
        }
    }

    public void init(FilterConfig arg0) {
        log.info("++++++++++++++++++++++++init++++++++++++++++++++++++");
    }

}
