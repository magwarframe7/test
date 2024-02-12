import os

# Function to crawl through directories
def crawl_file_share(doc_ids_file, root_path):
    with open(doc_ids_file, 'r') as f:
        for doc_id in f:
            doc_id = doc_id.strip()  # Remove trailing newline
            doc_id_path = os.path.join(root_path, doc_id)
            if os.path.exists(doc_id_path):
                for version_id in os.listdir(doc_id_path):
                    version_id_path = os.path.join(doc_id_path, version_id)
                    if os.path.isdir(version_id_path):
                        file_path = os.path.join(version_id_path, f"{doc_id}_0.pdf")
                        if os.path.exists(file_path):
                            print(f"Found file: {file_path}")
                        else:
                            print(f"No PDF file found for version_id {version_id} of doc_id {doc_id}")
                    else:
                        print(f"Invalid version_id directory: {version_id_path}")

# Example usage
if __name__ == "__main__":
    doc_ids_file = "list_of_doc_ids.txt"  # Path to the file containing doc IDs
    root_path = r"\\BASE\PATH"  # Root path of the file share
    crawl_file_share(doc_ids_file, root_path)
