import json
import os
import subprocess

from django.http import HttpResponse
from django.views import View

class CompilerView(View):
    def get(self, request):
        # <view logic>
        return HttpResponse('Herro')

    def post(self, request):
        body_unicode = request.body.decode('utf-8')
        body = json.loads(body_unicode)
        create_file(body.get('code'))
        return HttpResponse(handle_uploaded_file())

def create_file(buff):
    path = '.\\code\\'
    if not os.path.exists(path):
        os.makedirs(path)
    filename = 'main.cpp'
    with open(os.path.join(path, filename), 'wt') as temp_file:
        temp_file.write(buff)
        temp_file.close()

def handle_uploaded_file():
    try:
        subprocess.check_output("docker build . -t gcc", cwd="./code", shell=True)
        return subprocess.check_output("docker run --rm gcc", cwd="./code", shell=True)
    except Exception as e:
        return e
