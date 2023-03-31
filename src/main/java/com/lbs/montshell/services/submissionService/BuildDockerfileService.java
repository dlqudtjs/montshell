package com.lbs.montshell.services.submissionService;

import java.nio.file.Path;

public interface BuildDockerfileService {
String buildDockerfile(Path dockerfilePath, String dockerImageName);
}
