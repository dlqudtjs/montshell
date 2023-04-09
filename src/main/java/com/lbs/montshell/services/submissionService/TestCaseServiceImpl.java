package com.lbs.montshell.services.submissionService;

import com.lbs.montshell.repositories.JpaTestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TestCaseServiceImpl implements TestCaseService {
    private final Path TEMP_INPUT_FILE_PATH;
    private final Path TEMP_OUTPUT_FILE_PATH;
    JpaTestCaseRepository jpaTestCaseRepository;

    public TestCaseServiceImpl(@Qualifier("tempInputFilePath") Path TEMP_INPUT_FILE_PATH, @Qualifier("tempOutputFilePath") Path TEMP_OUTPUT_FILE_PATH, JpaTestCaseRepository jpaTestCaseRepository) {
        this.TEMP_INPUT_FILE_PATH = TEMP_INPUT_FILE_PATH;
        this.TEMP_OUTPUT_FILE_PATH = TEMP_OUTPUT_FILE_PATH;
        this.jpaTestCaseRepository = jpaTestCaseRepository;
    }

    @Override
    public String createInputFile(Long id) {
        try {
            // id 에 해당하는 input 을 가져온다.
            String input = jpaTestCaseRepository.findInputById(id);

            // TestCase 의 inputFileName 을 가져온다.
            String getInputFileName = getInputFileName(id);

            // TEMP_INPUT_FILE_PATH 에 getInputFileName 을 붙인다.
            Path inputFilePath = TEMP_INPUT_FILE_PATH.resolve(getInputFileName);

            // inputFilePath 에 input 을 저장한다.
            Files.write(inputFilePath, input.getBytes());

            return getInputFileName;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String createOutputFile(Long id) {
        try {
            // id 에 해당하는 output 를 가져온다.
            String output = jpaTestCaseRepository.findOutputById(id);

            // TestCase 의 outputFileName 을 가져온다.
            String getOutputFileName = getOutputFileName(id);

            // TEMP_OUTPUT_FILE_PATH 에 getOutputFileName 을 붙인다.
            Path outputFilePath = TEMP_OUTPUT_FILE_PATH.resolve(getOutputFileName);

            // outputFilePath 에 output 을 저장한다.
            Files.write(outputFilePath, output.getBytes());

            return getOutputFileName;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getInputFileName(Long id) {
        return "input_" + id + ".txt";
    }

    public String getOutputFileName(Long id) {
        return "output_" + id + ".txt";
    }
}
