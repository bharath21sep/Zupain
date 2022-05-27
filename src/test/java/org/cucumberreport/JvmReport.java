package org.cucumberreport;

import java.io.File;
import java.util.ArrayList;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

public class JvmReport{
	public static void generateJvmReport(String jsonpath) {
		File f = new File("G:\\selenium\\Zupain\\target\\reports");
		Configuration con = new Configuration(f, "ZUPAIN");
		con.addClassifications("Browser","chrome");
		con.addClassifications("version","");
		con.addClassifications("platform name", "Windows");
		 con .addClassifications("Sprint","");
		 con.addClassifications("Tester name","Bharath");
		 con.addClassifications("Date","26/05/2022");
	ArrayList <String> li = new ArrayList<String>();
	li.add(jsonpath);
	ReportBuilder r = new ReportBuilder(li, con);
	r.generateReports();
	
	}

	
}
