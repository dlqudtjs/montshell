package com.lbs.montshell.services.submissionService;

import java.io.IOException;
import java.nio.file.Path;

public interface CreateTempCodeFileService {
    Path createTempCodeFile(String code, String language) throws IOException;
}
