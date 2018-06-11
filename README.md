# SmartXMLAnalizer

Run application:
java -jar smartAnalyzer.jar <input_origin_file_path> <input_other_sample_file_path> <target_element_id>

Results:
sample-1-evil-gemini.html:
html[0]  > body[1]  > div[0]  > div[1]  > div[2]  > div[0]  > div[0]  > div[1]  > a

sample-2-container-and-clone.html:
html[0]  > body[1]  > div[0]  > div[1]  > div[2]  > div[0]  > div[0]  > div[1]  > div[0]  > a

sample-3-the-escape.html:
html[0]  > body[1]  > div[0]  > div[1]  > div[2]  > div[0]  > div[0]  > div[2]  > a

sample-4-the-mash.html:
html[0]  > body[1]  > div[0]  > div[1]  > div[2]  > div[0]  > div[0]  > div[2]  > a
