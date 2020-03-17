package cn.bytes.jtim.connector.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 基础处理
 *
 * @version 1.0
 * @date 2020/3/17 10:32
 */
@ControllerAdvice
public class BasicController {

//    /**
//     * 系统异常
//     *
//     * @param e
//     * @return <br>
//     * @since 1.0
//     */
//    @ExceptionHandler(value = {GlobalException.class, Exception.class})
//    @ResponseBody
//    public Result globalException(Exception e, HttpServletRequest request) {
//        LoggerUtils.error(LOG, "{}", e);
//        if (e instanceof GlobalException) {
//            GlobalException global = (GlobalException) e;
//            return Result.fail(global.getCode(), global.getMessage(), () -> request.getServletPath()).language(HeaderUtils.getLanguage(request));
//        }
//        return Result.fail(GlobalCode.C500, () -> request.getServletPath()).language(HeaderUtils.getLanguage(request));
//    }
//
//    /**
//     * 404 处理
//     *
//     * @param e
//     * @return <br>
//     * @since 1.0
//     */
//    @ExceptionHandler(value = {NoHandlerFoundException.class})
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    @ResponseBody
//    public Result notFoundException(NoHandlerFoundException e,
//                                    HttpServletRequest request) {
//        LoggerUtils.error(LOG, "{}", e);
//        return Result.fail(GlobalCode.C404.getCode(), e.getMessage(), () -> request.getServletPath()).language(HeaderUtils.getLanguage(request));
//    }
//
//    /**
//     * 不被支持的请求
//     *
//     * @param e
//     * @return <br>
//     * @since 1.0
//     */
//    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public Result badRequestException(HttpRequestMethodNotSupportedException e,
//                                      HttpServletRequest request) {
//        LoggerUtils.error(LOG, "{}", e);
//        return Result.fail(GlobalCode.C400.getCode(), e.getMessage(), () -> request.getServletPath()).language(HeaderUtils.getLanguage(request));
//    }
//
//    /**
//     * @param e
//     * @param request
//     * @return
//     * @Title: badRequestParmasException
//     * @Description: 不被支持的消息体
//     * @date 2018年9月6日下午11:20:42
//     */
//    @ExceptionHandler({HttpMessageNotReadableException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public Result badRequestParmasException(HttpMessageNotReadableException e,
//                                            HttpServletRequest request) {
//        LoggerUtils.error(LOG, "{}", e);
//        return Result.fail(GlobalCode.C400.getCode(), e.getMessage(), () -> request.getServletPath()).language(HeaderUtils.getLanguage(request));
//    }
//
//    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public Result badRequestMediaTypeException(HttpRequestMethodNotSupportedException e,
//                                               HttpServletRequest request) {
//        LoggerUtils.error(LOG, "{}", e);
//        return Result.fail(GlobalCode.C400.getCode(), e.getMessage(), () -> {
//            return request.getServletPath();
//        }).language(HeaderUtils.getLanguage(request));
//    }


}
