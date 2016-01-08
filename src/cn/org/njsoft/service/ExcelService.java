package cn.org.njsoft.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.org.njsoft.model.SignIn;
import cn.org.njsoft.model.User;

/**
 * 2015/12/11
 * 处理Excel的service
 * 用于：
 * 	1、将指定格式的公会会员信息 Excel文件导入系统。
 * 	2、在系统界面结束活动，可生成本次活动的详细信息报表
 * @see cn.org.njsoft.service#ExcelService
 * @author YXF
 */
@Scope("singleton")
@Service("excelService")
public interface ExcelService {
	List<User> readExcel(String path) throws Exception;
	// 从excel中读取文件,处理为文件流的形式，读取取出数据，存放在list里面
	String generateExcel(List<SignIn> signInList, String excelName) throws Exception;
	//通过signInList生产excel

}
