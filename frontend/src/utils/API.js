export const getAll = "http://localhost:8000/employee/all";
export const getOne = (id) =>
  fetch(`http://localhost:8000/employee/${id}`, {
    headers: new Headers({"Content-Type": "application/json; charset=utf-8"}),
    method: "GET"
  });
export const addRandom = () =>
  fetch("http://localhost:8000/employee/random", {
    headers: new Headers({"Content-Type": "application/json; charset=utf-8"}),
    method: "POST"
  });
export const update = (data) =>
  fetch("http://localhost:8000/employee", {
    headers: new Headers({"Content-Type": "application/json; charset=utf-8"}),
    method: "PUT",
    body: JSON.stringify(data)
  });
export const lockId = (id) =>
  fetch(`http://localhost:8000/employee/lock/${id}`, {
    headers: new Headers({"Content-Type": "application/json; charset=utf-8"}),
    method: "POST"
  });
export const unlockId = (id) =>
  fetch(`http://localhost:8000/employee/unlock/${id}`, {
    headers: new Headers({"Content-Type": "application/json; charset=utf-8"}),
    method: "POST"
  });
export const deleteId = (id) =>
  fetch(`http://localhost:8000/employee/${id}`, {
    headers: new Headers({"Content-Type": "application/json; charset=utf-8"}),
    method: "DELETE"
  });
