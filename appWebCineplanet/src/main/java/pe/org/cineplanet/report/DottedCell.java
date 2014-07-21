package pe.org.cineplanet.report;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

class DottedCell implements PdfPCellEvent {
    //@Override
    public void cellLayout(PdfPCell cell, Rectangle position,
        PdfContentByte[] canvases) {
        PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
        canvas.setLineDash(5f, 5f);
        canvas.rectangle(position.getLeft(), position.getBottom(),
            position.getWidth(), position.getHeight());
        canvas.stroke();
    }
}