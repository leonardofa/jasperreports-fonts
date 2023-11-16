package br.com.leonardo.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping("report")
public class ReportController {

	@GetMapping
	public ResponseEntity<byte[]> report() throws JRException {
		return ResponseEntity.ok().header("Content-Disposition", "inline; filename=report.pdf").contentType(MediaType.APPLICATION_PDF)
				.body(generate());
	}

	private byte[] generate() throws JRException {
		InputStream reportStream = getClass().getResourceAsStream("/reports/report.jasper");
		HashMap<String, Object> params = new HashMap<>();
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				reportStream, params, new JRBeanCollectionDataSource(List.of(new Entry("name1", "value1")))
		);
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}


	public static record Entry(String name, String value) {
	}

}
