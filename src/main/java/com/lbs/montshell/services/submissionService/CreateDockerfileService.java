package com.lbs.montshell.services.submissionService;

import java.io.IOException;
import java.nio.file.Path;

public interface CreateDockerfileService {
    Path createDockerfile(String language) throws IOException;
}
