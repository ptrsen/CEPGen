#!/usr/bin/python
import sys

filename = sys.argv[1]
filenameout = sys.argv[2]

f = open(filename, "r")
fout = open(filenameout, 'w')
for line in f:
  vwline = ""
  label = ""
  clas = ""
  if len(line)<4 or line.startswith("@"):
    continue
  strs = line.split(',')
  strs = map(lambda x: x.strip(), strs)
  label = strs[-1]
  if ((label=="cluster4") or (label=="1")):
    clas = "1"
  else:
    clas = "-1"

  vwline = clas+" "+label+ "|f "
  for i,e in enumerate(strs[:-1]):
    vwline += str(i) + ':' + e + " "
  
  fout.write(vwline)
  fout.write('\n')
 
f.close()
fout.close()
