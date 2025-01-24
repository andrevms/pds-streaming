class Music {
    constructor({ id, title, description, thumbnailUrl, animationUrl, categories, ratings, audioUrl, musicGenre, artists }) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.animationUrl = animationUrl;
        this.categories = categories;
        this.ratings = ratings;
        this.audioUrl = audioUrl;
        this.musicGenre = musicGenre;
        this.artists = artists;
    }
}

class Podcast {
    constructor({ id, title, description, thumbnailUrl, animationUrl, categories, ratings, audioUrl, videoUrl, hosts, guests }) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.animationUrl = animationUrl;
        this.categories = categories;
        this.ratings = ratings;
        this.audioUrl = audioUrl;
        this.videoUrl = videoUrl;
        this.hosts = hosts;
        this.guests = guests;
    }
}

const path = `http://localhost:8080`;

function loadMusic(musicId) {
    return fetch(`${path}/api/music/${musicId}`, {
        method: "GET"
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network answer was not ok.");
        }
        return response.json();
    })
    .then((music) => {
        return new Music({
            id: music.id,
            title: music.title,
            description: music.description,
            thumbnailUrl: music.thumbnailUrl,
            animationUrl: music.animationUrl,
            categories: music.categories,
            ratings: music.ratings,
            audioUrl: music.audioUrl,
            musicGenre: music.musicGenre,
            artists: music.artists
        });
    })
}

function loadMusics() {
    return fetch(`${path}/api/music`, {
        method: "GET"
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network answer was not ok.");
        }
        return response.json();
    })
    .then((music) => {
        const musicList = [];

        for (let key in music) {
            musicList.push(new Music({
                id: music[key].id,
                title: music[key].title,
                description: music[key].description,
                thumbnailUrl: music[key].thumbnailUrl,
                animationUrl: music[key].animationUrl,
                categories: music[key].categories,
                ratings: music[key].ratings,
                audioUrl: music[key].audioUrl,
                musicGenre: music[key].musicGenre,
                artists: music[key].artists
            }));
        }

        return musicList;
    });
}

function loadPodcast(podcastId) {
    return fetch(`${path}/api/podcasts/${podcastId}`, {
        method: "GET"
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network answer was not ok.");
        }
        return response.json();
    })
    .then((podcast) => {
        return new Podcast({
            id: podcast.id,
            title: podcast.title,
            description: podcast.description,
            thumbnailUrl: podcast.thumbnailUrl,
            animationUrl: podcast.animationUrl,
            categories: podcast.categories,
            ratings: podcast.ratings,
            audioUrl: podcast.audioUrl,
            videoUrl: podcast.videoUrl,
            hosts: podcast.hosts,
            guests: podcast.guests
        });
    })
}

function loadPodcasts() {
    return fetch(`${path}/api/podcasts`, {
        method: "GET"
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error("Network answer was not ok.");
        }
        return response.json();
    })
    .then((podcasts) => {
        const podcastsList = [];

        for (let key in podcasts) {
            podcastsList.push(new Podcast({
                id: podcasts[key].id,
                title: podcasts[key].title,
                description: podcasts[key].description,
                thumbnailUrl: podcasts[key].thumbnailUrl,
                animationUrl: podcasts[key].animationUrl,
                categories: podcasts[key].categories,
                ratings: podcasts[key].ratings,
                audioUrl: podcasts[key].audioUrl,
                videoUrl: podcasts[key].videoUrl,
                hosts: podcasts[key].hosts,
                guests: podcasts[key].guests
            }));
        }

        return podcastsList;
    })
}

export { loadMusic, loadMusics, loadPodcast, loadPodcasts };