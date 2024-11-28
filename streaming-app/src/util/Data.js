class Movie {
    constructor({ id, title, description, videoUrl, thumbnailUrl, animationUrl, categories, ratings, ratingsAverage }) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.animationUrl = animationUrl;
        this.categories = categories;
        this.ratings = ratings;
        this.ratingsAverage = ratingsAverage;
    }
}

class TvShow {
    constructor({ id, title, description, thumbnailUrl, animationUrl, seasons, categories, ratings, ratingsAverage }) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.animationUrl = animationUrl;
        this.seasons = seasons;
        this.categories = categories;
        this.ratings = ratings;
        this.ratingsAverage = ratingsAverage;
    }
}

class Season {
    constructor({ id, title, description, thumbnailUrl, animationUrl, episodes }) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.animationUrl = animationUrl;
        this.episodes = episodes;
    }
}

class Episode {
    constructor({ id, title, description, videoUrl, thumbnailUrl, animationUrl }) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.animationUrl = animationUrl;
    }
}

class Rating {
    constructor({ id, stars, timestamp }) {
        this.id = id;
        this.stars = stars;
        this.timestamp = timestamp;
    }
}

const path = `http://localhost:8080`;

function loadMovie(movieId) {
    return fetch(`${path}/api/movies/${movieId}`, {
        method: "GET"
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network answer was not ok.");
        }
        return response.json();
    })
    .then((movie) => {
        return new Movie({
            id: movie.id,
            title: movie.title,
            description: movie.description,
            videoUrl: movie.videoUrl,
            thumbnailUrl: movie.thumbnailUrl,
            animationUrl: movie.animationUrl,
            categories: movie.categories,
            ratings: movie.ratings,
            ratingsAverage: movie.ratingsAverage
        });
    })
}

function loadMovies() {
    return fetch(`${path}/api/movies`, {
        method: "GET"
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network answer was not ok.");
        }
        return response.json();
    })
    .then((movies) => {
        const moviesList = [];

        for (let key in movies) {
            moviesList.push(new Movie({
                id: movies[key].id,
                title: movies[key].title,
                description: movies[key].description,
                videoUrl: movies[key].videoUrl,
                thumbnailUrl: movies[key].thumbnailUrl,
                animationUrl: movies[key].animationUrl,
                categories: movies[key].categories,
                ratings: movies[key].ratings,
                ratingsAverage: movies[key].ratingsAverage
            }));
        }

        return moviesList;
    });
}

function loadTvShow(tvShowId) {
    return fetch(`${path}/api/tvshows/${tvShowId}`, {
        method: "GET"
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network answer was not ok.");
        }
        return response.json();
    })
    .then((tvShow) => {
        return new TvShow({
            id: tvShow.id,
            title: tvShow.title,
            description: tvShow.description,
            thumbnailUrl: tvShow.thumbnailUrl,
            animationUrl: tvShow.animationUrl,
            seasons: tvShow.seasons,
            categories: tvShow.categories,
            ratings: tvShow.ratings,
            ratingsAverage: tvShow.ratingsAverage
        });
    })
}

function loadTvShows() {
    return fetch(`${path}/api/tvshows`, {
        method: "GET"
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network answer was not ok.");
        }
        return response.json();
    })
    .then((tvShows) => {
        const tvShowsList = [];

        for (let key in tvShows) {
            tvShowsList.push(new TvShow({
                id: tvShows[key].id,
                title: tvShows[key].title,
                description: tvShows[key].description,
                thumbnailUrl: tvShows[key].thumbnailUrl,
                animationUrl: tvShows[key].animationUrl,
                seasons: tvShows[key].seasons,
                categories: tvShows[key].categories,
                ratings: tvShows[key].ratings,
                ratingsAverage: tvShows[key].ratingsAverage
            }));
        }

        return tvShowsList;
    })
}

function loadSeason(seasonId) {
    return fetch(`${path}/api/seasons/${seasonId}`, {
        method: "GET"
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network answer was not ok.");
        }
        return response.json();
    })
    .then((season) => {
        return new Season({
            id: season.id,
            title: season.title,
            description: season.description,
            thumbnailUrl: season.thumbnailUrl,
            animationUrl: season.animationUrl,
            episodes: season.episodes
        });
    });
}

function loadSeasons() {
    return fetch(`${path}/api/seasons`, {
        method: "GET"
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network answer was not ok.");
        }
        return response.json();
    })
    .then((seasons) => {
        const seasonsList = [];

        for (let key in seasons) {
            seasonsList.push(new Season({
                id: seasons[key].id,
                title: seasons[key].title,
                description: seasons[key].description,
                thumbnailUrl: seasons[key].thumbnailUrl,
                animationUrl: seasons[key].animationUrl,
                episodes: seasons[key].episodes
            }));
        }

        return seasonsList;
    });
}

function loadSeasonsByTvShowId(tvShowId) {
    return fetch(`${path}/api/seasons/tvShow?tvShowId=${tvShowId}`, {
        method: "GET"
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network answer was not ok.");
        }
        return response.json();
    })
    .then((seasons) => {
        const seasonsList = [];

        for (let key in seasons) {
            seasonsList.push(new Season({
                id: seasons[key].id,
                title: seasons[key].title,
                description: seasons[key].description,
                thumbnailUrl: seasons[key].thumbnailUrl,
                animationUrl: seasons[key].animationUrl,
                episodes: seasons[key].episodes
            }));
        }

        return seasonsList;
    });
}

function loadEpisode(episodeId) {
    return fetch(`${path}/api/episodes/${episodeId}`, {
        method: "GET"
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network answer was not ok.");
        }
        return response.json();
    })
    .then((episode) => {
        console.log(episode);
        return new Episode({
            id: episode.id,
            title: episode.title,
            description: episode.description,
            videoUrl: episode.videoUrl,
            thumbnailUrl: episode.thumbnailUrl,
            animationUrl: episode.animationUrl
        });
    });
}

function loadEpisodes() {
    return fetch(`${path}/api/episodes`, {
        method: "GET"
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network answer was not ok.");
        }
        return response.json();
    })
    .then((episodes) => {
        const episodesList = [];

        for (let key in episodes) {
            episodesList.push(new Episode({
                id: episodes[key].id,
                title: episodes[key].title,
                description: episodes[key].description,
                videoUrl: episodes[key].videoUrl,
                thumbnailUrl: episodes[key].thumbnailUrl,
                animationUrl: episodes[key].animationUrl
            }))
        }

        return episodesList;
    });
}

function loadEpisodesBySeasonId(seasonId) {
    return fetch(`${path}/api/episodes/season?seasonId=${seasonId}`, {
        method: "GET"
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network answer was not ok.");
        }
        return response.json();
    })
    .then((episodes) => {
        const episodesList = [];

        for (let key in episodes) {
            episodesList.push(new Episode({
                id: episodes[key].id,
                title: episodes[key].title,
                description: episodes[key].description,
                videoUrl: episodes[key].videoUrl,
                thumbnailUrl: episodes[key].thumbnailUrl,
                animationUrl: episodes[key].animationUrl
            }))
        }

        return episodesList;
    });
}

export { loadMovie, loadMovies, loadTvShow, loadTvShows, loadSeason, loadSeasons, loadSeasonsByTvShowId, loadEpisode, loadEpisodes, loadEpisodesBySeasonId };