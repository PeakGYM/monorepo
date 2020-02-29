[@react.component]
let make = (~content, ~className=?, ~style=?) => {
  <span ?className ?style> content->React.string </span>;
};