//package it.marco.marco.cloud.interceptor;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.core.MethodParameter;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
//import it.marco.marco.bean.http.Outcome;
//import it.marco.marco.bean.http.Type;
//import it.marco.marco.bean.http.Widget;
//import it.marco.marco.bean.http.WrapperResponse;
//
//@ControllerAdvice
//public class ResponseInterceptor implements ResponseBodyAdvice<Object> {
//	
//    @Override
//    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> converterType, ServerHttpRequest request, ServerHttpResponse response) {
//
//    	HttpServletResponse res = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
//
////		System.out.println("BODY = " + body.toString());
////    	System.out.println("RETURN TYPE = " + returnType);
////    	System.out.println("SELECTED CONTENT TYPE = " + selectedContentType);
//    	
//    	if (body.getClass().getCanonicalName().contains("springfox")) {
//    		return body;
//    	} else if (!HttpStatus.valueOf(res.getStatus()).is2xxSuccessful()) {
//    		
////        	return ResponseEntity
////        				.status(res.getStatus())
////        				.body(new WrapperResponse(null, Arrays.asList(new Message(HttpStatus.valueOf(res.getStatus()), body.toString(), Type.ERROR, null, Widget.ALERT))));
//        	
////        	response.setStatusCode(HttpStatus.valueOf(res.getStatus()));
//        	return new WrapperResponse(null, new Outcome(HttpStatus.valueOf(res.getStatus()), body.toString(), Type.ERROR, null, Widget.ALERT));
//        	
////    		return new WrapperResponse(null, Arrays.asList(HttpStatus.valueOf(res.getStatus())), Arrays.asList(body.toString()), Arrays.asList(Type.ERROR), null, Arrays.asList(Widget.ALERT));
//    	} else {
//    		
//    		return new WrapperResponse(body, null);
//    	}
//    }
//}