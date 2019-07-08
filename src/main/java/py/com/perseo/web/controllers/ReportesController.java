package py.com.perseo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
public class ReportesController {

	// public static String path_reports = "/opt/apache-tomcat-8.0.33/temp/";
	public static String path_reports = "/tmp/";

	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	@ResponseBody
	public void downloadFile(@WebParam(name = "filename") String filename, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			OutputStream outputStream = null;
			InputStream in = null;
			try {
				in = new FileInputStream(path_reports + filename);
				byte[] buffer = new byte[1024];
				int bytesRead = 0;
				response.setContentType("application/x-pdf");
				response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
				outputStream = response.getOutputStream();
				while (0 < (bytesRead = in.read(buffer))) {
					outputStream.write(buffer, 0, bytesRead);
				}
			} finally {
				if (null != in) {
					in.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/getFile", method = RequestMethod.GET)
	@ResponseBody
	public void getFile(@WebParam(name = "filename") String filename, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			OutputStream outputStream = null;
			InputStream in = null;

			try {

				in = new FileInputStream(
						request.getSession().getServletContext().getContextPath() + "/resources/reportes/" + filename);

				// at /tmp
				byte[] buffer = new byte[1024];
				int bytesRead = 0;
				response.setContentType("application/x-pdf");
				response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
				outputStream = response.getOutputStream();
				while (0 < (bytesRead = in.read(buffer))) {
					outputStream.write(buffer, 0, bytesRead);
				}
			} finally {
				if (null != in) {
					in.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
