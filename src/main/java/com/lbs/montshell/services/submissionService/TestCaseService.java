package com.lbs.montshell.services.submissionService;

import java.io.IOException;
import java.nio.file.Path;

public interface TestCaseService {
    String createInputFile(Long id) throws IOException;
    String createOutputFile(Long id);
}
