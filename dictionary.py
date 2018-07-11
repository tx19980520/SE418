import json
f = open("dictionary.txt","r");
dataList = [];
lines = f.readlines();
for line in lines:
    data = line.replace("\n", "");
    dataList.append(data);
json_dicts = json.dumps(dataList,indent=4)
f.close();
f = open("dictionary.json","w");
f.write(json_dicts);

    
