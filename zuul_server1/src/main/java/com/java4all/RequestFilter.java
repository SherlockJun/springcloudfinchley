package com.java4all;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * description:
 *
 * @author wangzhongxiang
 * @date 2018/11/29 16:47
 */
@Component
public class RequestFilter extends ZuulFilter{

  private static Logger logger = LoggerFactory.getLogger(RequestFilter.class);

  /**
   * pre 路由之前
   * routing 路由之时
   * post 路由之后
   * error 发送错误调用
   * @return
   */
  @Override
  public String filterType() {
    return "pre";
  }

  /**过滤的顺序*/
  @Override
  public int filterOrder() {
    return 0;
  }

  /**这里可以写逻辑判断，是否要过滤，本文true,永远过滤*/
  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() throws ZuulException {
    RequestContext currentContext = RequestContext.getCurrentContext();
    HttpServletRequest request = currentContext.getRequest();
    String token = request.getParameter("token");
    if(StringUtils.isEmpty(token)){
      logger.warn("=======>token is empty");
      currentContext.setSendZuulResponse(false);
      currentContext.setResponseStatusCode(401);
      try {
        currentContext.getResponse().getWriter().write("token is empty");
      }catch (Exception ex){
        ex.printStackTrace();
      }
    }
    return null;
  }
}
