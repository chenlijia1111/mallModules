package com.github.chenlijia1111.mallweb.controller;

import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.utils.core.WebFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * 文件上传返回相对路径,方便迁移
 * @author chenlijia
 * @version 1.0
 * @since 2019/12/3 0003 下午 3:26
 **/
@Controller
@RequestMapping("system")
@Api(tags = "文件接口")
public class FileController {

    @Value("${upload.imgSavePath}")
    private String imgSavePath;

    @Value("${upload.fileSavePath}")
    private String fileSavePath;


    /**
     * 上传文件接口
     *
     * @param fileType   文件类型  img/file
     * @param file
     * @param isFileName 是否需要源文件名称
     * @return
     */
    @PostMapping(value = "upload")
    @ResponseBody
    @ApiOperation(value = "上传文件接口", notes = "返回文件下载地址,fileType: 文件类型  img/file;isfileName: 是否需要源文件名称")
    public Result uploadFile(@RequestParam(defaultValue = "img") String fileType, MultipartFile file,
                             @RequestParam(defaultValue = "false") boolean isFileName) {
        Result result = WebFileUtil.saveFile(file, fileType.equals("img") ? imgSavePath : fileSavePath, "system/downLoad", isFileName, fileType);
        return result;
    }


    /**
     * 上传文件接口
     * <p>
     * 前端代码如下
     * <input type="file" name="file" multiple="multiple" onchange="showPreview(this)">
     * function showPreview(source) {
     * var files = source.files;
     * console.log(files);
     * <p>
     * var formData = new FormData();
     * formData.append("fileType", "file");
     * formData.append("isfileName", "false");
     * for (var i = 0; i < files.length; i++) {
     * formData.append("files", files[i]);
     * }
     * $.ajax(
     * {
     * url: "http://127.0.0.1:8087/ende/system/upload/batch",
     * data: formData,
     * type: "POST",
     * processData: false,
     * contentType: false,
     * success: function (res) {
     * console.log(res);
     * }
     * }
     * );
     * }
     *
     * @param fileType   文件类型  img/file
     * @param files
     * @param isFileName 是否需要源文件名称
     * @return
     */
    @PostMapping(value = "upload/batch")
    @ResponseBody
    @ApiOperation(value = "批量上传文件接口", notes = "返回文件下载地址,fileType: 文件类型  img/file;isfileName: 是否需要源文件名称,files 文件数组")
    public Result batchUploadFile(@RequestParam(defaultValue = "img") String fileType,
                                  MultipartFile[] files,
                                  @RequestParam(defaultValue = "false") boolean isFileName) {

        if (null == files || files.length == 0) {
            return Result.failure("上传文件为空");
        }

        ArrayList<Object> resultPath = new ArrayList<>();
        for (MultipartFile file : files) {
            Result result = WebFileUtil.saveFile(file, fileType.equals("img") ? imgSavePath : fileSavePath, "system/downLoad", isFileName, fileType);
            if (!result.getSuccess()) {
                return result;
            }
            resultPath.add(result.getData());

        }
        return Result.success("上传成功", resultPath);
    }

    /**
     * @param fileType 文件类型 img,qr,file
     * @param filePath 文件地址
     * @param fileName 文件下载返回名称
     * @param isDelete 下载之后是否删除
     * @param response
     */
    @GetMapping(value = "downLoad")
    @ApiOperation(value = "下载文件接口")
    public void downLoadFile(@RequestParam(defaultValue = "file") String fileType, String filePath, String fileName,
                             @RequestParam(defaultValue = "false") boolean isDelete, HttpServletResponse response, HttpServletRequest request) {
        String pathSuffix = "";
        switch (fileType) {
            case "img":
                pathSuffix = imgSavePath;
                break;
            case "file":
                pathSuffix = fileSavePath;
                break;
        }
        //二维码保存路径为绝对路径，不需要前缀
        WebFileUtil.downloadFile(StringUtils.isNotEmpty(pathSuffix) ? (pathSuffix + "/" + filePath) : filePath, fileName, isDelete, response, request);
    }
}
