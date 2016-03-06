import deepFreeze from 'deep-freeze';

export default {
    routing: {},
    appData: {
        featureListData: deepFreeze({
            items: [
                {
                    imageSrc: 'http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png',
                    header: 'Share',
                    desc: 'Share a Week planner to provide visibility into everyone\'s available times and budget'
                },
                {
                    imageSrc: 'http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png',
                    header: 'Search Events',
                    desc: 'Search for events that matches everyone\'s preferences such as budget and availability'
                },
                {
                    imageSrc: 'http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png',
                    header: 'Plan',
                    desc: 'Plan with others by sending event suggestions and updates'
                }
            ]
        }),
        weekData: {
            weekId: null,
            timestamp: new Date().toISOString(),
            entity: {
                users: [],
                createdOn: new Date().toISOString()
            },
            eventsData : {
                map: {},
                fetching: {
                    status: null,
                    statusText: null
                }
            },
            fetching: {
                status: null,
                statusText: null
            }
        },
        userData: {
            userId: null,
            entity: null,
            fetching: {
                status: null,
                statusText: null
            }
        },
        colorMap : { // color to user
            '#ffff00': null, //yellow
            '#ff9900': null, //orange
            pink: null,
            red: null,
            green: null,
            blue : null,
            purple: null,
            brown: null
        },
        eventsData : {
            entities: {
                _embedded : {
                    events: []
                }
            },
            query: {},
            fetching: {
                status: null,
                statusText: null
            }
        }
    },
    form: {
        createWeekForm: {
            email: null,
            placeholder: "Enter your email"
        }
    }
};
