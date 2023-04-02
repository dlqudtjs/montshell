package com.lbs.montshell.services.submissionService;

import com.lbs.montshell.controllers.SubmitForm;

import java.io.IOException;
import java.nio.file.Path;

public interface CreateTempCodeFileService {
    String createTempCodeFile(SubmitForm submitForm) throws IOException;
}
