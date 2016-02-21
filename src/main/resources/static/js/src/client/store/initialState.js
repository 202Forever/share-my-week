export default {
    routing: {
        location: {
            pathname : '/'
        }
    },
    appData: {
        featureListData: {
            items: [{
                imageSrc: 'http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png',
                header: "Share",
                desc: "Share a Week planner to provide visibility into everyone's available times and budget"
            }, {
                imageSrc: 'http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png',
                header: "Search Events",
                desc: "Search for events that matches everyone's preferences such as budget and availability"
            }, {
                imageSrc: 'http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png',
                header: "Plan",
                desc: 'Plan with others by sending event suggestions and updates'
            }]
        },
        weekData: {
            entity: {},
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