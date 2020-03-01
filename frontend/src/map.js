import React from "react";
import { render } from "react-dom";
import { Map, Marker, Popup, TileLayer } from "react-leaflet";

let myPosition = [50.0262886, 19.9518396];

export function loadMap(position, gyms, onMapClick, onMarkerClick) {
  let gymIcon = new L.Icon({
    iconUrl:
      "https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-blue.png",
    shadowUrl:
      "https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png",
    iconSize: [75, 123],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
  });

  let clientIcon = new L.Icon({
    iconUrl:
      "https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-violet.png",
    shadowUrl:
      "https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png",
    iconSize: [75, 123],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
  });

  let formatCoachCount = coachIds => {
    let count = coachIds.length;

    return count === 1 ? "1 coach" : `${count} coaches`;
  };

  let gymMarkers = gyms.map(gym => {
    let pos = [gym.location.lat, gym.location.lng];
    // let text = `${gym.name} - ${formatCoachCount(gym.coachIds)}`;
    let text = gym.name;

    return (
      <Marker
        key={gym.id}
        riseOnHover={true}
        icon={gymIcon}
        position={pos}
        onClick={_ => onMarkerClick(gym)}
      >
        <Popup>
          <div style={{ fontSize: "28px" }}>{text}</div>
        </Popup>
      </Marker>
    );
  });

  let map = (
    <Map
      center={position}
      zoom={16}
      minZoom={10}
      maxZoom={18}
      onClick={onMapClick}
      doubleClickZoom={false}
    >
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
      />
      <Marker icon={clientIcon} position={myPosition}>
        <Popup>
          <div style={{ fontSize: "28px" }}>Your location</div>
        </Popup>
      </Marker>
      {gymMarkers}
    </Map>
  );

  render(map, document.getElementById("map-container"));
}

export function windowHeight() {
  return window.innerHeight;
}
