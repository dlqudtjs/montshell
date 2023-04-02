package com.lbs.montshell.services.submissionService;

import com.lbs.montshell.controllers.SubmitForm;

import java.io.IOException;
import java.nio.file.Path;

public interface CreateDockerfileService {
    Path createDockerfile(SubmitForm submitForm, String codeFileName) throws IOException;
}
