#!/usr/bin/env python

# Copyright 2017 Google Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""Demonstrates web detection using the Google Cloud Vision API.

Example usage:
  python web_detect.py https://goo.gl/X4qcB6
  python web_detect.py ../detect/resources/landmark.jpg
  python web_detect.py gs://your-bucket/image.png
"""
# [START vision_web_detection_tutorial]
# [START vision_web_detection_tutorial_imports]
import argparse
import io


from google.cloud import vision
from google.cloud.vision import types

import logging
import json
from flask import Flask
from flask import request
from flask import make_response

#from google.appengine.api import images
import base64
from io import BytesIO
#from django.core.files.base import ContentFile
#from django.core.files.uploadedfile import InMemoryUploadedFile, TemporaryUploadedFile

app = Flask(__name__)
JSON_MIME_TYPE = 'application/json'
# [END vision_web_detection_tutorial_imports]
#return json_response(json.dumps({'image_url': result}))
@app.route('/vision', methods=['GET'])
def annotate():
    bucket = request.args.get('bucket')
    image = request.args.get('image')
    process = request.args.get('process')
    path = "gs://" + bucket + "/" + image
    """Returns web annotations given the path to an image."""
    # [START vision_web_detection_tutorial_annotate]
    client = vision.ImageAnnotatorClient()

    if path.startswith('http') or path.startswith('gs:'):
        image = types.Image()
        image.source.image_uri = path

    else:
        with io.open(path, 'rb') as image_file:
            content = image_file.read()

        image = types.Image(content=content)
    if process == 'vision':
        web_detection = client.web_detection(image=image).web_detection
        # [END vision_web_detection_tutorial_annotate]
        myResult = report(web_detection)
        return json_response(json.dumps(myResult, sort_keys=True))
    elif process == 'compress':
        # Download the image
        #new_image = BytesIO()
        #foo = images.Image.open(image)
        #foo.save(new_image,"after" + '.jpg', format="JPEG", quality=10, optimize=True, compress_level=5)
        #new_image = ContentFile(new_image.getvalue())
        #return InMemoryUploadedFile(new_image, None, image.name, image.content_type, None, None)
        return "Compress"
    


def report(annotations):
    """Prints detected features in the provided web annotations."""
    # [START vision_web_detection_tutorial_print_annotations]
    result = {}
    if annotations.pages_with_matching_images:
        print('\n{} Pages with matching images retrieved'.format(
            len(annotations.pages_with_matching_images)))

        for page in annotations.pages_with_matching_images:
            print('Url   : {}'.format(page.url))

    if annotations.full_matching_images:
        print('\n{} Full Matches found: '.format(
              len(annotations.full_matching_images)))

        for image in annotations.full_matching_images:
            print('Url  : {}'.format(image.url))

    if annotations.partial_matching_images:
        print('\n{} Partial Matches found: '.format(
              len(annotations.partial_matching_images)))

        for image in annotations.partial_matching_images:
            print('Url  : {}'.format(image.url))

    if annotations.web_entities:
        print('\n{} Web entities found: '.format(
              len(annotations.web_entities)))
        
        for entity in annotations.web_entities:
            print('Score      : {}'.format(entity.score))
            print('Description: {}'.format(entity.description))
            result['{}'.format(entity.description)] = '{}'.format(entity.score)
    
    return result
    #return json_response(json.dumps({'result': "result"}))

    # [END vision_web_detection_tutorial_print_annotations]

def json_response(data='', status=200, headers=None):
    headers = headers or {}
    if 'Content-Type' not in headers:
        headers['Content-Type'] = JSON_MIME_TYPE

    return make_response(data, status, headers)
if __name__ == '__main__':
   app.run(host='127.0.0.1',port=8080,debug=True)
    # [END vision_web_detection_tutorial_run_application]
# [END vision_web_detection_tutorial]
