import deepFreeze from 'deep-freeze';

export default {
    routing: {
        location: {
            pathname: '/'
        }
    },
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
            timestamp: new Date().toISOString(),
            entity: {
                users: [],
                createdOn: new Date().toISOString()
            },
            fetching: {
                status: null,
                statusText: null
            }
        },
        userData: {
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
        }
    },
    form: {
        createWeekForm: {
            email: null,
            placeholder: "Enter your email"
        }
    }
};
