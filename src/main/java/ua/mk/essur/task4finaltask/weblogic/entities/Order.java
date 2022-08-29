package ua.mk.essur.task4finaltask.weblogic.entities;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Integer id;

    @Column(name = "file_order_report", length = 100)
    private String fileOrderReport;

    @Column(name = "order_report_creating_date")
    private LocalDateTime orderReportCreatingDate;

    public Order(String fileOrderReport, LocalDateTime orderReportCreatingDate) {
        this.fileOrderReport = fileOrderReport;
        this.orderReportCreatingDate = orderReportCreatingDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileOrderReport() {
        return fileOrderReport;
    }

    public void setFileOrderReport(String fileOrderReport) {
        this.fileOrderReport = fileOrderReport;
    }

    public LocalDateTime getOrderCreatingDate() {
        return orderReportCreatingDate;
    }

    public void setOrderCreatingDate(LocalDateTime orderReportCreatingDate) {
        this.orderReportCreatingDate = orderReportCreatingDate;
    }

    @Override
    public String toString() {
        return "{ id =" + id +
                ", file name ='" + fileOrderReport + '\'' +
                ", creating date=" + orderReportCreatingDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)) +
                '}' + "\n";
    }
}