package com.chistadata.Domain.Billing.Auxilary;

import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFOutput {
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private ArrayList<?> arr = null;

    public PDFOutput(ArrayList<?> a)
    {
        arr = a;
    }

    public void GeneratePDF(String FILE) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            //addMetaData(document);
            addTitlePage(document);
            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addContent(Document document) throws DocumentException {
        // TODO Auto-generated method stub
        createTable(document);
    }

    private void addTitlePage(Document document) throws DocumentException {
        // TODO Auto-generated method stub
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Billing Report", catFont));

        addEmptyLine(preface, 2);
        document.add(preface);

    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        // TODO Auto-generated method stub
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }

    }


    private  void createTable(Document document)
            throws DocumentException {
        // Dynamically generates rows and columns
        ArrayList<?> colArr0 = (ArrayList<?>) arr.get(0);
        PdfPTable table = new PdfPTable(colArr0.size());

        for(int i = 0; i < arr.size(); i++)
        {
            ArrayList<?> colArr = (ArrayList<?>)arr.get(i);
            for(int j = 0; j < colArr.size(); j++)
            {
                PdfPCell c1 = new PdfPCell(new Phrase(colArr.get(j).toString()));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setFixedHeight(20);
                table.addCell(c1);

            }

        }

        document.add(table);

    }

}
