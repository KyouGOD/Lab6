package com.example.lab6;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        }

        List<String> formattedLines = formatText(lines);

        File formattedFile = new File(getServletContext().getRealPath("/") + "formatted.txt");
        try (FileWriter writer = new FileWriter(formattedFile)) {
            for (String line : formattedLines) {
                writer.write(line + System.lineSeparator());
            }
        }

        request.setAttribute("downloadPath", "formatted.txt");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private List<String> formatText(List<String> lines) {
        List<String> formattedLines = new ArrayList<>();
        StringBuilder paragraph = new StringBuilder("   ");
        for (String line : lines) {
            if (line.isEmpty()) {
                formattedLines.addAll(wrapText(paragraph.toString()));
                paragraph.setLength(0);
                paragraph.append("   ");
                formattedLines.add("");
            } else {
                paragraph.append(" ").append(line);
            }
        }
        if (paragraph.length() > 3) {
            formattedLines.addAll(wrapText(paragraph.toString()));
        }
        return formattedLines;
    }

    private List<String> wrapText(String text) {
        List<String> wrappedLines = new ArrayList<>();
        while (text.length() > 80) {
            int breakPoint = 80;
            while (breakPoint > 0 && text.charAt(breakPoint) != ' ') {
                breakPoint--;
            }
            if (breakPoint == 0) breakPoint = 80;
            wrappedLines.add(text.substring(0, breakPoint));
            text = "   " + text.substring(breakPoint).trim();
        }
        wrappedLines.add(text);
        return wrappedLines;
    }
}
