package cn.org.njsoft.dao.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import cn.org.njsoft.dao.ExcelDao;
import cn.org.njsoft.model.SignGift;
import cn.org.njsoft.model.SignIn;
import cn.org.njsoft.model.User;
import cn.org.njsoft.model.UserType;

/**
 * 2015/10/12 ExcelDao接口的实现类
 * 
 * @see cn.org.njsoft.dao.impl#ExcelDaoImpl
 * @author YXF
 *
 */
@Service
public class ExcelDaoImpl implements ExcelDao {

	/**
	 * 2015/12/12 excel 分为两个版本2003-2007和2010两个版本， 即excel的后缀名为：xls和xlsx。
	 * 该方法就是来判断该excel是xlsx还是xls类型
	 * 
	 * @see cn.org.njsoft.dao.ExcelDao#readExcel(String)
	 * @author YXF
	 */
	public List<User> readExcel(String path) throws Exception {// path上传文件的路径
		List<User> userList = new ArrayList<User>();
		// 将文件流处理
		InputStream in = new FileInputStream(path);
		// markSupported() 测试此输入流是否支持
		if (!in.markSupported()) {
			// 创建的流对象具有一个长度为8byte的回推缓存，从而允许将多个字节回推到输入流中。
			in = new PushbackInputStream(in, 8);
		}
		// 该文件是xls文件，调用readXls方法
		if (POIFSFileSystem.hasPOIFSHeader(in)) {
			userList = readXls(in);
			// 该文件是xlsx文件，调用readXlsx方法
		} else if (POIXMLDocument.hasOOXMLHeader(in)) {
			userList = readXlsx(in);
		} else {
			// 如果该文件既不是xls文件，又不是xlsx文件，弹窗提示该文件类型不符合要求
			ServletActionContext
					.getResponse()
					.getWriter()
					.println(
							"<script language='javascript'>alert('错误：该文件类型不符合要求');history.go(-1)</script>");
			userList = null;
		}
		return userList;
	}

	/**
	 * 2015/12/12 从xlsx中读取文件,处理为文件流的形式，读取取出数据，存放在list里面
	 * 
	 * @see cn.org.njsoft.dao.ExcelDao#readXlsx(InputStream)
	 * @author YXF
	 */
	public List<User> readXlsx(InputStream in) throws Exception {// path上传文件的路径
		// 防止alert出现乱码问题
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// flag是用来判断第一行的数据是否符合约定格式，如果不符合，返回null
		boolean flag = true;
		User user = null;
		PrintWriter writer = response.getWriter();
		// 将读取到的文件放在userList
		List<User> userList = new ArrayList<User>();
		// 将通过路径将excel文件流处理
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(in);
		XSSFRow xssfRow;
		// 读表格
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			// 读取完毕，跳出循环
			if (xssfSheet == null) {
				continue;
			}
			// 注意读表格中的行列
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					// new 一个user将一行读取到的数据存放在一个user里
					user = new User();
					XSSFCell userName = xssfRow.getCell(0);// 获取第一列的数据
					XSSFCell userSex = xssfRow.getCell(1);// 获取第二列的数据
					XSSFCell userAge = xssfRow.getCell(2);// 获取第三列的数据
					XSSFCell userTrueName = xssfRow.getCell(3);// 获取第四列的数据
					XSSFCell userPhone = xssfRow.getCell(4);// 获取第五列的数据
					XSSFCell userMail = xssfRow.getCell(5);// 获取第六列的数据
					XSSFCell userTypeId = xssfRow.getCell(6);// 获取第七列的数据
					XSSFCell userPassword = xssfRow.getCell(7);// 获取第八列的数据
					int usertypeid;
					// 验证改行是否有空格存在
					if (userName != null && userSex != null && userAge != null
							&& userTrueName != null && userPhone != null
							&& userMail != null && userTypeId != null
							&& userPassword != null) {
						// userName为空，提示信息，跳出循环
						if (getValue(userName).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行登录名为一个空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// userSex为空，提示信息，跳出循环
						if (getValue(userSex).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行用户性别为空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// userAge为空，提示信息，跳出循环
						if (getValue(userAge).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行用户年龄为一个空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// userTrueName为空，提示信息，跳出循环
						if (getValue(userTrueName).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行用户真实姓名为空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// userPhone为空，提示信息，跳出循环
						if (getValue(userPhone).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行手机号码为一个空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// userMail为空，提示信息，跳出循环
						if (getValue(userMail).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行用户邮箱为一个空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// userTypeId为空，提示信息，跳出循环
						if (getValue(userTypeId).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行用户类别为一个空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// userPassword为空，提示信息，跳出循环
						if (getValue(userPassword).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行用户密码一个空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}

						// 将usertypeid转换为int类型
						usertypeid = Integer.parseInt(getValue(userTypeId));
						// 验证userTypeId是否为1,2,3,4以外的值
						if (usertypeid != 1 && usertypeid != 2
								&& usertypeid != 3 && usertypeid != 4) {
							// usertypeid不在1,2,3,4范围里面，提示多少行的usertypeid有误
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行的用户类型错误，插入失败,请审核');history.go(-1)</script>");
							flag = false;
							break;
						}
						// 调用属性为XSSFCell的getValue，将转换后的值放入user里
						user.setUserName(getValue(userName));
						user.setUserSex(getValue(userSex));
						user.setUserAge(Integer.parseInt(getValue(userAge)));
						user.setUserTrueName(getValue(userTrueName));
						user.setUserPhone(getValue(userPhone));
						user.setUserMail(getValue(userMail));
						// new 一个新的userType存放通过usertypeid
						UserType userType = new UserType();
						userType.setTypeId(usertypeid);
						user.setUserType(userType);
						user.setUserPassword(getValue(userPassword));
						// user的UserState默认设置为1
						user.setUserState(1);
						// 将该行对应的user存放在userList
						userList.add(user);
					}
				}
			}
		}
		/*
		 * 如果exel的数据通过了各种验证，flag为true。 否则任何一种验证不通过都会设置flag为flase
		 */
		if (flag) {
			return userList;
		} else {
			return null;
		}
	}

	/**
	 * 2015/12/12 处理2003-2007版本的后缀名为xls版本的excel
	 * 从xlx中读取文件,处理为文件流的形式，读取取出数据，存放在list里面
	 * 
	 * @see cn.org.njsoft.dao.ExcelDao#readXls(InputStream)
	 * @author YXF
	 */
	public List<User> readXls(InputStream in) throws Exception {

		// 防止alert出现乱码问题
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		// flag是用来判断第一行的数据是否符合约定格式，如果不符合，返回null
		boolean flag = true;
		User user = null;

		PrintWriter writer = response.getWriter();
		// 将读取到的文件放在userList
		List<User> userList = new ArrayList<User>();

		// 将通过路径将excel文件流处理
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(in);
		// 读表格
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			// 读取完毕，跳出循环
			if (hssfSheet == null) {
				continue;
			}
			// 注意读表格中的行列
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					// new 一个user将一行读取到的数据存放在一个user里
					user = new User();
					HSSFCell userName = hssfRow.getCell(0);// 获取第一列的数据
					HSSFCell userSex = hssfRow.getCell(1);// 获取第二列的数据
					HSSFCell userAge = hssfRow.getCell(2);// 获取第三列的数据
					HSSFCell userTrueName = hssfRow.getCell(3);// 获取第四列的数据
					HSSFCell userPhone = hssfRow.getCell(4);// 获取第五列的数据
					HSSFCell userMail = hssfRow.getCell(5);// 获取第六列的数据
					HSSFCell userTypeId = hssfRow.getCell(6);// 获取第七列的数据
					HSSFCell userPassword = hssfRow.getCell(7);// 获取第八列的数据

					int usertypeid;
					// 验证改行是否有空格存在
					if (userName != null && userSex != null && userAge != null
							&& userTrueName != null && userPhone != null
							&& userMail != null && userTypeId != null
							&& userPassword != null) {
						// userName为空，提示信息，跳出循环
						if (getValue(userName).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行登录名名为一个空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// userSex为空，提示信息，跳出循环
						if (getValue(userSex).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行用户性别为空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// userAge为空，提示信息，跳出循环
						if (getValue(userAge).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行用户年龄为一个空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// userTrueName为空，提示信息，跳出循环
						if (getValue(userTrueName).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行用户真实姓名为空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// userPhone为空，提示信息，跳出循环
						if (getValue(userPhone).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行手机号码为一个空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// userMail为空，提示信息，跳出循环
						if (getValue(userMail).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行用户邮箱为一个空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// userTypeId为空，提示信息，跳出循环
						if (getValue(userTypeId).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行用户类别为一个空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// userPassword为空，提示信息，跳出循环
						if (getValue(userPassword).equals("")) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行用户密码一个空值,插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// 将usertypeid转换为int类型
						usertypeid = Integer.parseInt(getValue(userTypeId));
						// 验证userTypeId是否为1,2,3,4以外的值
						if (usertypeid != 1 && usertypeid != 2
								&& usertypeid != 3 && usertypeid != 4) {
							writer.println(" <script language='javascript'>alert('错误：在第"
									+ (rowNum + 1)
									+ "行的用户类型错误，插入失败');history.go(-1)</script>");
							flag = false;
							break;
						}
						// 调用属性为HSSFCell的getValue，将转换后的值放入user里
						user.setUserName(getValue(userName));
						user.setUserSex(getValue(userSex));
						user.setUserAge(Integer.parseInt(getValue(userAge)));
						user.setUserTrueName(getValue(userTrueName));
						user.setUserPhone(getValue(userPhone));
						user.setUserMail(getValue(userMail));
						// new 一个新的userType存放usertypeid
						UserType userType = new UserType();
						userType.setTypeId(usertypeid);
						user.setUserType(userType);
						user.setUserPassword(getValue(userPassword));
						// user的UserState默认设置为1
						user.setUserState(1);
						// 将该行对应的user存放在userList
						userList.add(user);
					}
				}
			}
		}
		if (flag) {
			return userList;
		} else {
			return null;
		}

	}

	/**
	 * 2015/12/12 xlsx类型excel数据类型转换方法 判断单元格的数据为什么类型，统一转换为string类型
	 * 
	 * @see cn.org.njsoft.dao.ExcelDao#getValue(HSSFCell)
	 * @author YXF
	 */
	@SuppressWarnings("static-access")
	private String getValue(HSSFCell hssfCell) {
		String value = "";
		// hssfCell.getCellType()获取类型，判断类型是否为boolean类型
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			value = String.valueOf(hssfCell.getBooleanCellValue());
			// hssfCell.getCellType()获取类型，判断类型是否为数值型
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			BigDecimal big = new BigDecimal(hssfCell.getNumericCellValue());
			value = big.toString();
			// 解决1234.0 去掉后面的.0
			if (null != value && !"".equals(value.trim())) {
				String[] item = value.split("[.]");
				if (1 < item.length && "0".equals(item[1])) {
					value = item[0];
				}
			}
			// xssfRow.getCellType()获取类型，判断类型是否为空格
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BLANK) {
		} else {
			value = String.valueOf(hssfCell.getStringCellValue());
		}

		return value;
	}

	/**
	 * 2015/12/12 xls类型excel数据类型转换方法 判断单元格的数据为什么类型，统一转换为string类型
	 * 
	 * @see cn.org.njsoft.dao.ExcelDao#getValue(HSSFCell)
	 * @author YXF
	 */
	@SuppressWarnings("static-access")
	public String getValue(XSSFCell xssfRow) {
		String value = "";
		// xssfRow.getCellType()获取类型，判断类型是否为boolean类型
		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			value = String.valueOf(xssfRow.getBooleanCellValue());
			// xssfRow.getCellType()获取类型，判断类型是否为数值型
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			BigDecimal big = new BigDecimal(xssfRow.getNumericCellValue());
			value = big.toString();
			// 解决1234.0 去掉后面的.0
			if (null != value && !"".equals(value.trim())) {
				String[] item = value.split("[.]");
				if (1 < item.length && "0".equals(item[1])) {
					value = item[0];
				}
			}
			// xssfRow.getCellType()获取类型，判断类型是否为空格
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BLANK) {
		} else {
			value = String.valueOf(xssfRow.getStringCellValue());
		}
		return value;
	}

	/**
	 * 2015/12/12 导出数据到excel
	 * 
	 * @see cn.org.njsoft.dao.ExcelDao#generateExcel(List<SignIn>, excelName)
	 * @author YXF
	 */
	public String generateExcel(List<SignIn> signInList, String excelName)
			throws Exception {
		
		// 生成的excel的头部
		String[] tableHeader = { "姓名", "用户类型", "性别", "工号", "手机号码", "邮箱", "礼品",
				"活动名", "活动地点" };
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头

		// 设置数据字体
		HSSFFont cellFont = workbook.createFont();
		cellFont.setFontName("宋体");
		cellFont.setFontHeightInPoints((short) 10);
		// 设置数据样式
		HSSFCellStyle cellstyle = workbook.createCellStyle();
		cellstyle.setFont(cellFont);
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 设置标题字体
		HSSFFont headfont = workbook.createFont();
		headfont.setFontName("黑体");
		headfont.setFontHeightInPoints((short) 13);// 字体大小
		// 设置标题样式
		HSSFCellStyle headstyle = workbook.createCellStyle();
		headstyle.setFont(headfont);
		headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		headstyle.setLocked(true);
		headstyle.setWrapText(true);// 自动换行
		try {
			if (signInList.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter("信息表");
				row = sheet.createRow(0);
				row.setHeight((short) 300);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					sheet.setColumnWidth(k, 4000);// 设置列的宽度
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					cell.setCellStyle(headstyle);// 设置头部样式
				}
				for (int i = 0; i < signInList.size(); i++) {
					// 礼品
					String gift =new String();
					SignIn SignIn = (SignIn) signInList.get(i);// 获取SignIn对象
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					// 添加真实名字
					cell = row.createCell(0); // 创建第i+1行第2列
					cell.setCellValue(SignIn.getSignUser().getUserTrueName());// 设置第i+1行第2列的值
					cell.setCellStyle(cellstyle); // 设置风格

					// 添加用户类型
					cell = row.createCell(1); // 创建第i+1行第2列
					cell.setCellValue(SignIn.getSignUser().getUserType()
							.getTypeName());// 设置第i+1行第2列的值
					cell.setCellStyle(cellstyle); // 设置风格

					// 添加性别
					cell = row.createCell(2);// 创建第i+1行第3列
					cell.setCellValue(SignIn.getSignUser().getUserSex());// 设置第i+1行第3列的值
					cell.setCellStyle(cellstyle);// 设置风格

					// 添加工号
					cell = row.createCell(3);// 创建第i+1行第4列
					cell.setCellValue(new HSSFRichTextString(SignIn
							.getSignUser().getUserName()));// 设置第i+1行第4列的值
					cell.setCellStyle(cellstyle);// 设置风格

					// 添加手机号码
					cell = row.createCell(4);// 创建第i+1行第5列
					cell.setCellValue(new HSSFRichTextString(SignIn
							.getSignUser().getUserPhone()));// 设置第i+1行第5列的值
					cell.setCellStyle(cellstyle);// 设置风格

					// 邮箱
					cell = row.createCell(5);// 创建第i+1行第6列
					cell.setCellValue(SignIn.getSignUser().getUserMail());// 设置第i+1行第6列的值
					cell.setCellStyle(cellstyle);// 设置风格

					// 礼品
					Set<SignGift> signGift = SignIn.getSignGift();
					// 判断是否有礼品 非会员或者会员未收到礼品，将礼品栏位显示未收到礼品
					if (SignIn.getSignUser().getUserType().getTypeId() == 3
							|| SignIn.getGiftState() == 0|| SignIn.getGiftState() == 2
							|| signGift.isEmpty() == true||signGift==null) {
						cell = row.createCell(6);// 创建第i+1行第6列
						cell.setCellValue("未收到礼品");// 设置第i+1行第6列的值
						cell.setCellStyle(cellstyle);// 设置风格
					} else {
						
							Iterator<SignGift> it = signGift.iterator();
							while (it.hasNext()){
								// 判断是否有下一个
								gift += it.next().getLinkGiftId().getGiftName()
										+ "";
						}
						cell = row.createCell(6);// 创建第i+1行第6列
						cell.setCellValue(gift);// 设置第i+1行第6列的值
						cell.setCellStyle(cellstyle);// 设置风格
					}
					// 添加活动名
					cell = row.createCell(7);// 创建第i+1行第1列
					cell.setCellValue(SignIn.getSignAct().getActName());// 设置第i+1行第1列的值
					cell.setCellStyle(cellstyle);// 设置风格

					// 活动地点
					cell = row.createCell(8);// 创建第i+1行第7列
					cell.setCellValue(SignIn.getSignAct().getActAddress());// 设置第i+1行第7列的值
					cell.setCellStyle(cellstyle);// 设置风格
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();
			// 生成的excel的名字
			String headerStr = excelName;
			headerStr = new String(headerStr.getBytes("gb2312"), "ISO8859-1");// headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr + ".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=UTF-8");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			// 调用HSSFWorkbook类的write方法写入到输出流
			workbook.write(out);
			// 将缓存区的内容强制写出
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭out
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
