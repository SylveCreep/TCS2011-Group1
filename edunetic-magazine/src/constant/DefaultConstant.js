export const DefaultConstants = Object.freeze({
    Limit: 15,
    Column: 'id',
    Sort: 'asc',
    Page: 1,
    female: 0,
    male: 1,
    firstPage: 1,
    Role: {
        Admin : 1,
        MarketingManager : 2,
        MarketingCoordinator :3,
        Student : 4,
        Guest: 5
    },
    ContributionStatuses: {
        Pending: 1,
        Approved: 2,
        Denied: 0
    },
    MagazineStatuses:{
        Opening: 0,
        Processing: 1,
        Publising: 2,
        Closed: 3,
    }
});
