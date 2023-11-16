package br.com.leonardo.controller;

import lombok.SneakyThrows;
import lombok.val;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("reports")
public class ReportController {

    @GetMapping("{report}")
    public ResponseEntity<byte[]> report(@PathVariable final String report) throws JRException {
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=report.pdf").contentType(MediaType.APPLICATION_PDF)
                .body(generate(report));
    }

    @SneakyThrows
    private byte[] generate(final String report) {
        val reportStream = new FileInputStream("/reports/%s.jasper".formatted(report));
        val params = new HashMap<String, Object>();
        val jasperPrint = JasperFillManager.fillReport(
                reportStream, params, new JRBeanCollectionDataSource(List.of(new Entry("name1", "value1")))
        );
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }


    public record Entry(String name, String value) {
    }

}
