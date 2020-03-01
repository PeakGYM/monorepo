let toNumber = v =>
  switch (v) {
  | Some(v) => Some(v / 10)
  | None => None
  };