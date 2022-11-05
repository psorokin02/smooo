export const BASE_URL = 'http://localhost:8080'

export function initStepSMO(configuration) {
    return fetch(BASE_URL + '/api/step/init', {
        method: 'POST',
        body: JSON.stringify(configuration),
        headers: {
            "content-type": "application/json"
        },
    })
        .then(resp => resp.json())
}

export function makeStep() {
    return fetch(BASE_URL + '/api/step/make-step', {
        method: 'POST',
        headers: {
            "content-type": "application/json"
        },
    })
        .then(resp => resp.json())
}