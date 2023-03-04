package com.hromov.library.service;

import jakarta.servlet.http.HttpServletResponse;

public interface FileExportService {
    void exportBooks(HttpServletResponse httpServletResponse);
}
