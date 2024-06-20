package com.hisedu.common.exception.file;

import com.hisedu.common.exception.base.BaseException;

/**
 * 文件信息异常类
 * 
 * @author hisedu
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
