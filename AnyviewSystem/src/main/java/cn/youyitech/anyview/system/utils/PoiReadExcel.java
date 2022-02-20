package cn.youyitech.anyview.system.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.youyitech.anyview.system.service.*;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.youyitech.anyview.system.dto.StudentAndUser;
import cn.youyitech.anyview.system.entity.ClassAndStudent;
import cn.youyitech.anyview.system.entity.ClassEntity;
import cn.youyitech.anyview.system.entity.College;
import cn.youyitech.anyview.system.entity.Major;
import cn.youyitech.anyview.system.entity.School;
import cn.youyitech.anyview.system.entity.Student;
import cn.youyitech.anyview.system.support.Setting;

/**
 * POI解析Excel文件内容
 * 
 * @author zzq
 * @param
 */

@Component
public class PoiReadExcel {

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private CollegeService collegeService;

	@Autowired
	private MajorService majorService;

	@Autowired
	private ClassService classService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private ClassAndStudentService classAndStudentService;

	// 学生集合
	List<Student> lists_student;
	// 班级与学生集合
	List<ClassAndStudent> lists_classAndStudent;
	// 用来记录该学生属于班级的数量
	List<Integer> lists_number;
	// 用来记录该学生的用户名
	List<String> lists_username;
	// 用来记录该学生的学校
	List<Long> lists_schoolId;
	// 学生
	Student student;
	// 学生与班级关系表
	ClassAndStudent classAndStudent;

	// 标志位
	boolean isClass = false;
	boolean student_flag = true;
	boolean isStudent = false;
	boolean saveClassAndStudentInfo = true;

	String userName = "";
	String schoolName = "";
	String collegeName = "";
	String majorName = "";
	long schoolId = 0;
	long collegeId = 0;
	long majorId = 0;
	int year = 0;

	// 解析学生信息
	public StudentAndUser readStudentExcel(String path) {
		lists_student = new ArrayList<>();
		lists_classAndStudent = new ArrayList<>();
		lists_number = new ArrayList<>();
		lists_username = new ArrayList<>();
		lists_schoolId = new ArrayList<>();
		// 需要解析的Excel文件
		Setting setting = SettingUtils.get();
		String newpath = setting.getUploadPath() + path;
		// String newpath = System.getProperty("user.dir") + "/src/main/webapp/"
		// + path;
		File file = new File(newpath);
		FileInputStream fi = null;
		try {
			fi = FileUtils.openInputStream(file);
			// 创建Excel，读取文件内容
			HSSFWorkbook workbook = new HSSFWorkbook(fi);
			// 读取默认第一个工作表sheet
			HSSFSheet sheet = workbook.getSheetAt(0);
			int firstRowNum = 2;
			// 获取sheet中最后一行行号
			int lastRowNum = sheet.getLastRowNum();
			for (int i = firstRowNum; i <= lastRowNum; i++) {
				HSSFRow row = sheet.getRow(i);
				// 获取当前行最后单元格列号
				//int lastCellNum = row.getLastCellNum();
				int lastCellNum = 10;
				student_flag = true;
				for (int j = 0; j < lastCellNum; j++) {
					if (student_flag) {
						HSSFCell cell = row.getCell(j);
						// 标志位记录学生的学校，学院，专业和班级是否存在
						String value;
						if (cell == null) {
							value = "";
						} else {
							value = getStringValueFromCell(cell);
						}
						setStudentData(j, value);
					}
				}
			}
			StudentAndUser studentAndUser = new StudentAndUser();
			// 把获取到的数据保存到dto
			studentAndUser.setLists_student(lists_student);
			studentAndUser.setLists_classAndStudent(lists_classAndStudent);
			studentAndUser.setLists_number(lists_number);
			return studentAndUser;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fi != null) {
					fi.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	// 设置学生信息
	private void setStudentData(int j, String value) {

		switch (j) {
		case 0:
			student = new Student();
			student.setName(value);
			break;
		case 1:
			userName = "";
			if (!value.equals("") && value != null) {
				student.setUsername(value);
				userName = value;
			}
			if (value.equals("")) {
				student_flag = true;
			}

			break;
		case 2:
			if (!"".equals(value) && value != null) {
				student.setSex(value);
			}
			break;
		case 3:
			// 获取全部的学校信息
			if (!"".equals(value) && value != null) {
				if (!schoolName.equals(value)) {
					School school = schoolService.findBySchoolName(value);
					if (school != null) {
						schoolId = school.getId();
						schoolName = school.getSchoolName();
						collegeName = "";
						majorName = "";
					}
				}
				student.setSchoolId((int) schoolId);
				Student temp = new Student();
				temp.setSchoolId((int) schoolId);
				temp.setUsername(userName);
				if (studentService.findByUserNameAndSchoolId(temp) != null) {
					isStudent = true;
				}
				//比较表中相同的学校有没有相同的用户名
				for (int i = 0; i < lists_username.size(); i++) {
					if (lists_username.get(i).equals(userName) && lists_schoolId.get(i) == schoolId) {
						isStudent = true;
					}
				}
			} else {
				student_flag = false;
			}
			break;
		case 4:
			// 检验数据库中该学校是否拥有该学院
			if (schoolId > 0) {
				if (!"".equals(value) && value != null) {
					if (!collegeName.equals(value)) {
						College temp = new College();
						temp.setCollegeName(value);
						temp.setSchoolId((int) schoolId);
						College college = collegeService.findByCollegeNameAndSchoolId(temp);
						collegeId = college.getId();
						collegeName = college.getCollegeName();
						majorName = "";
					}
				}

				if (collegeId <= 0) {
					student_flag = false;
				}
			}

			if (value == null || "".equals(value)) {
				student_flag = false;
			}

			break;
		case 5:
			if (collegeId > 0) {
				if (!"".equals(value) && value != null) {
					if (!majorName.equals(value)) {
						Major temp = new Major();
						temp.setCollegeId(collegeId);
						temp.setMajorName(value);
						Major major = majorService.findByCollegeIdAndMajorName(temp);
						majorId = major.getId();
						majorName = major.getMajorName();
					}
				}

				if (majorId <= 0) {
					student_flag = false;
				}

			}
			if (value == null || "".equals(value)) {
				student_flag = false;
			}

			break;
		case 6:
			if (!"".equals(value) && value != null) {
				year = Integer.parseInt(value);
			}
			break;
		case 7:
			// 判断该班级是否存在
			if (majorId > 0) {

				if (value.contains(",")) {
					String[] classArray = value.split(",");
					// List<ClassAndStudent> temp_lists = new ArrayList<>();
					for (int a = 0; a < classArray.length; a++) {
						String className = classArray[a];
						ClassEntity temp = new ClassEntity();
						temp.setMajor_id((int) majorId);
						temp.setClassName(className);
						temp.setYear(year);
						ClassEntity classEntity = classService.findByMajorIdAndClassName(temp);
						if (classEntity != null) {
							classAndStudent = new ClassAndStudent();
							classAndStudent.setStudent_class_id(classEntity.getId());
							// temp_lists.add(classAndStudent);
							lists_classAndStudent.add(classAndStudent);
							isClass = true;
						} else {
							student_flag = false;
						}
					}

					// if (temp_lists.size() != classArray.length) {
					// student_flag = false;
					// } else {
					// lists_number.add(classArray.length);
					// for (int k = 0; k < temp_lists.size(); k++) {
					// lists_classAndStudent.add(temp_lists.get(k));
					// }
					// }

				} else {
					ClassEntity temp = new ClassEntity();
					temp.setMajor_id((int) majorId);
					temp.setClassName(value);
					temp.setYear(year);
					ClassEntity classEntity = classService.findByMajorIdAndClassName(temp);
					if (classEntity != null) {
						if(isStudent == true){
							isStudent = false;
							Student tempS = new Student();
							tempS.setUsername(userName);
							tempS.setSchoolId((int)schoolId);
							long sid = studentService.findByUserNameAndSchoolId(tempS).getId();
							List<ClassAndStudent> classAndStudentList = classAndStudentService.findList("student_id",sid);
							for(int i = 0;i < classAndStudentList.size();i++){
								if (classAndStudentList.get(i).getStudent_class_id() == classEntity.getId()){
									saveClassAndStudentInfo =false;
									student_flag = false;
									break;
								}
							}
						}
						if(saveClassAndStudentInfo == true){
							classAndStudent = new ClassAndStudent();
							classAndStudent.setStudent_class_id(classEntity.getId());
							lists_classAndStudent.add(classAndStudent);
							lists_number.add(1);
						}
						isClass = true;
						saveClassAndStudentInfo = true;
						if (classAndStudent.getStudent_class_id() <= 0) {
							student_flag = false;
						}
					} else {
						student_flag = false;
					}
				}
			}

			if (value.equals("")) {
				student_flag = false;
			}

			break;
		case 8:
			if (!"".equals(value) && value != null) {
				student.setState(value);
			}
			break;
		case 9:

			if (!"".equals(value) && value != null) {
				student.setEmail(value);
			}
			if (isClass) {
				lists_student.add(student);
				lists_username.add(student.getUsername());
				lists_schoolId.add(schoolId);
				isClass = false;
			}
			break;

		}

	}

	public String getStringValueFromCell(Cell cell) {
		SimpleDateFormat sFormat = new SimpleDateFormat("MM/dd/yyyy");
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		String cellValue = "";
		if (cell == null) {
			cellValue = null;
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			cellValue = cell.getStringCellValue();
		}

		else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				double d = cell.getNumericCellValue();
				Date date = HSSFDateUtil.getJavaDate(d);
				cellValue = sFormat.format(date);
			} else {
				cellValue = decimalFormat.format((cell.getNumericCellValue()));
			}
		} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			cellValue = "";
		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			cellValue = String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
			cellValue = "";
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			cellValue = cell.getCellFormula().toString();
		}
		return cellValue;
	}

}