export function loadMap(
  payload,
  style,
  onChangeBounds,
  updateLatitude,
  updateLongitude,
  key,
  bounds,
) {
  const L = require("leaflet");
  // require("leaflet.vectorgrid/dist/Leaflet.VectorGrid.bundled");

    const position = [51.505, -0.09]
    const map = L.map(key).setView(position, 13)

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
    }).addTo(map)

    L.marker(position)
    .addTo(map)
    .bindPopup('A pretty CSS3 popup. <br> Easily customizable.')

    return map;

  // // Fixes lines between tiles
  // // https://github.com/Leaflet/Leaflet/issues/3575#issuecomment-150544739
  // let originalInitTile = L.GridLayer.prototype._initTile;
  // L.GridLayer.include({
  //   _initTile: function(tile) {
  //     originalInitTile.call(this, tile);

  //     let tileSize = this.getTileSize();

  //     tile.style.width = tileSize.x + 1 + "px";
  //     tile.style.height = tileSize.y + 1 + "px";
  //   }
  // });

  // let map = L.map(key, payload);

  // setTimeout(() => {
  //   map.invalidateSize();
  //   if (payload.zoom) {
  //     onChangeBounds(
  //       map
  //         .getBounds()
  //         .toBBoxString()
  //         .split(",")
  //     );
  //   }

  //   fitBounds(map, bounds);

  //   map.on("zoomend", () => {
  //     onChangeBounds(
  //       map
  //         .getBounds()
  //         .toBBoxString()
  //         .split(",")
  //     );
  //   });

  //   map.on("moveend", () => {
  //     map.invalidateSize();
  //     let LatLng = map.getCenter();
  //     updateLatitude(String(LatLng.lat));
  //     updateLongitude(String(LatLng.lng));
  //     onChangeBounds(
  //       map
  //         .getBounds()
  //         .toBBoxString()
  //         .split(",")
  //     );
  //   });
  // }, 300);

  return map;
}

export function fitBounds(map, bounds) {
  try {
    if (bounds && Array.isArray(bounds) && bounds.length >= 4) {
      map.fitBounds(
        L.latLngBounds(
          L.latLng(bounds[1], bounds[0]),
          L.latLng(bounds[3], bounds[2])
        ),
        { duration: 0, maxZoom: 18 }
      );
    }
  } catch (e) {}
}

export function reorderBounds(bounds) {
  return [bounds[2], bounds[0], bounds[3], bounds[1]];
}
