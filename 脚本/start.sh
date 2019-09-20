#!/bin/bash
echo Starting application 
nohup java -jar datacenter.jar>/dev/null 2>&1 &
