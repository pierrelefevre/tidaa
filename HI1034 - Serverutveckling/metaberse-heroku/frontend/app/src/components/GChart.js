import Chart from "react-google-charts";
import { useState, useEffect } from 'react';
import axios from 'axios';
import API_URL from "../API_URL";

const GChart = (props) => {
    const [chartData, setChartData] = useState(null);
    const [chartDataData, setChartDataData] = useState(null);

    useEffect(() => {
        axios.get(API_URL.chart + '/byId/?id=' + props.id)
            .then(function (response) {
                setChartData(response.data.chartSubmitData)
                console.log(response.data)
                var data = response.data.chartSubmitData.data;

                data.splice(0, 0, ['x', response.data.chartSubmitData.labels.series])
                setChartDataData(data)
            });
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [props.type, props.content]);

    return (
        <>
            {chartData != null ?
                <Chart
                    chartType="LineChart"
                    loader={<div>Loading Chart</div>}
                    data={chartDataData}
                    options={{
                        hAxis: {
                            title: chartData.labels.xaxis,
                        },
                        vAxis: {
                            title: chartData.labels.yaxis,
                        },
                    }}
                    rootProps={{ 'data-testid': '1' }}
                /> :
                "Loading chart..."
            }
        </>
    )

}

export default GChart