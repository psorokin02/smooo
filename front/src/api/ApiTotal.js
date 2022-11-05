import {BASE_URL} from "@/api/ApiStep";

export function getTotalStatistic(configuration) {
    return fetch(BASE_URL + '/api/total', {
        method: 'POST',
        body: JSON.stringify(configuration),
        headers: {
            "content-type": "application/json"
        },
    })
        .then(resp => resp.json())
}
