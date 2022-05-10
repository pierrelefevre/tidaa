import json
from flask import Flask, request
from joblib import load
import numpy as np

from sklearn.preprocessing import MinMaxScaler
from sklearn.linear_model import SGDRegressor
from sklearn.preprocessing import PolynomialFeatures

clf = load('nyc_taxi_model copy.joblib') 
poly = load('nyc_taxi_features copy.joblib') 
scale = load('nyc_taxi_scale copy.joblib') 

app = Flask(__name__)

@app.route('/', methods=['GET'])
def predict():
    origlat     = float(request.args.get('origlat'))
    origlon     = float(request.args.get('origlon'))
    destlat     = float(request.args.get('destlat'))
    destlon     = float(request.args.get('destlon'))
    pickup_time = float(request.args.get('pickup_time'))
    distance = ((((destlon - origlon )**2) + ((destlat - origlat)**2) )**0.5)
    
    
    data = [[origlat, origlon, destlat, destlon, pickup_time, distance]]
    
    print(str(data))
    
    data = np.array(data).reshape(1, -1)
    x_scaled = scale.transform(data)        
    poly_features = poly.transform(x_scaled)
    answer = clf.predict(poly_features)
    print(answer)
    
    return str(float(answer[0])*60.0)
    
app.run(debug=True)